package telegram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.omertron.themoviedbapi.MovieDbException;

import dialog.Dialog;
import storage.APIHandler;
import storage.VotesDatabase;
import structures.Field;
import structures.User;
import utils.UserUtils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TelegramBot extends TelegramLongPollingBot {

	private String bot_username;
	private String bot_token;
	private Map<String, Map<Field, List<String>>> idTotalFieldMap;
	private Map<String, Field> idCurrentFieldMap;
	private APIHandler apiDatabase;
	private VotesDatabase votesDatabase;
	private Map<String, DialogState> userDialogState;

	public TelegramBot(APIHandler apiDatabase, VotesDatabase votesDatabase, String username, String token) {
		this.bot_username = username;
		this.bot_token = token;
		this.apiDatabase = apiDatabase;
		this.votesDatabase = votesDatabase;
		idTotalFieldMap = new HashMap<String, Map<Field, List<String>>>();
		idCurrentFieldMap = new HashMap<String, Field>();
		userDialogState = new HashMap<String, DialogState>();
	}

	public TelegramBot(APIHandler apiDatabase, VotesDatabase votesDatabase, String username, String token, DefaultBotOptions options) {
		super(options);
		this.bot_username = username;
		this.bot_token = token;
		this.apiDatabase = apiDatabase;
		this.votesDatabase = votesDatabase;
		idTotalFieldMap = new HashMap<String, Map<Field, List<String>>>();
		idCurrentFieldMap = new HashMap<String, Field>();
		userDialogState = new HashMap<String, DialogState>();
	}

	private String processInput(String input, String username, String chatId) throws MovieDbException, IOException {
		User user = UserUtils.getUser(username, chatId);
		Dialog dialog = new Dialog(user, apiDatabase, votesDatabase);
		String answer;
		if (input.equals("/start"))
			answer = dialog.startDialog();
		else
			answer = dialog.processInput(input);
		try {
			UserUtils.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}

	@Override
	public void onUpdateReceived(Update update) {

		if (update.hasCallbackQuery())
		{
			CallbackQuery query = update.getCallbackQuery();
			try {
				processCallback(query.getFrom().getFirstName(),
						String.valueOf(query.getMessage().getChatId()), 
						query);
			}
			catch(MovieDbException | IOException ex)
			{
				ex.printStackTrace();
			}
			
			return;
		}
		
		Message inputMessage = update.getMessage();
		String inputCommand = inputMessage.getText();
		String id = inputMessage.getChatId().toString();
		String userFirstName = inputMessage.getFrom().getFirstName();

		if (userDialogState.get(id) == null)
			userDialogState.put(id, DialogState.BASIC);

		DialogState state = userDialogState.get(id);

		System.out.println(userFirstName + ": " + inputCommand);
		try {
			State newState = getState(inputCommand, id, state);
			String answer = getAnswer(newState, userFirstName, id);

			idCurrentFieldMap.put(id, newState.currentField);

			userDialogState.put(id, newState.newState);

			
			
			if (isFilmAnswer(answer))
			{
				sendMessage(prepareFilmSendMessage(answer, inputMessage.getChatId()));
				sendMessage(prepareOrdinaryMessage("Press next to get next film", inputMessage.getChatId(), newState));
			}
			else
				sendMessage(prepareOrdinaryMessage(answer, inputMessage.getChatId(), newState));
			
		} catch (MovieDbException | IOException e) {
			// e.printStackTrace();
		}
	}
	
	private SendMessage prepareOrdinaryMessage(String answer, long chatId, State newState)
	{
		SendMessage message = new SendMessage();
		message.setText(answer);
		message.setChatId(chatId);
		message.setReplyMarkup(newState.getKeyboard());
		
		return message;
	}
	
	private SendMessage prepareFilmSendMessage(String answer, long chatId)
	{
		String filmName = extractFilmName(answer);
		SendMessage message = new SendMessage();
		message.setText(answer);
		message.setChatId(chatId);
		
		attachVotingButtons(filmName, message);
		return message;
	}
	
	private void sendMessage(SendMessage message)
	{
		try {
			execute(message);
		} catch (TelegramApiException e) {
			// e.printStackTrace();
		}
	}
	
	private void processCallback(String username, String chatId, CallbackQuery query) throws MovieDbException, IOException
	{
		String queryText = query.getData();
		String rawAnswer = processInput(queryText, username, chatId);
		
		AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(query.getId());
        answer.setText(rawAnswer);
        answer.setShowAlert(true);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
	
	private boolean isFilmAnswer(String answer)
	{
		Pattern p = Pattern.compile("^(.+)\nРейтинг: [0-9]+$");
		Matcher m = p.matcher(answer);
		
		return m.matches();
	}
	
	private String extractFilmName(String answer)
	{
		Pattern p = Pattern.compile("^(.+)\nРейтинг: [0-9]+$");
		Matcher m = p.matcher(answer);
		m.find();
		return m.group(1);
	}
	
	private void attachVotingButtons(String filmName, SendMessage message)
	{
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(new InlineKeyboardButton().setText("Like").setCallbackData("/like " + filmName));
        row.add(new InlineKeyboardButton().setText("Dislike").setCallbackData("/dislike " + filmName));
        buttons.add(row);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
        message.setReplyMarkup(markupKeyboard);
	}

	public String getAnswer(State state, String username, String id) throws MovieDbException, IOException {
		return state.command == null ? state.answerString : processInput(state.command, username, id);
	}

	public State getState(String input, String chatId, DialogState state) throws MovieDbException {
		if (idTotalFieldMap.get(chatId) == null)
			idTotalFieldMap.put(chatId, new HashMap<Field, List<String>>());
		State currentState = new State(state, idTotalFieldMap.get(chatId), apiDatabase, idCurrentFieldMap.get(chatId));
		currentState.processInput(input);
		return currentState;
	}

	@Override
	public String getBotUsername() {
		return bot_username;
	}

	@Override
	public String getBotToken() {
		return bot_token;
	}

}
