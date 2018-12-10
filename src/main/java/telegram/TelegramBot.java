package telegram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import dialog.Dialog;
import dialog.Phrases;
import storage.FilmDatabase;
import storage.FilmRatingsDatabase;
import structures.Field;
import structures.User;
import utils.UserUtils;

public class TelegramBot extends TelegramLongPollingBot {

	private String bot_username;
	private String bot_token;
	private Map<String, Map<Field, List<String>>> idTotalFieldMap;
	private Map<String, Field> idCurrentFieldMap;
	private FilmDatabase database;
	private FilmRatingsDatabase ratingsDatabase;
	private Map<String, DialogState> userDialogState;

	public TelegramBot(FilmDatabase database, FilmRatingsDatabase ratingsDatabase, String username, String token) {
		this.bot_username = username;
		this.bot_token = token;
		this.database = database;
		this.ratingsDatabase = ratingsDatabase;
		idTotalFieldMap = new HashMap<String, Map<Field, List<String>>>();
		idCurrentFieldMap = new HashMap<String, Field>();
		userDialogState = new HashMap<String, DialogState>();
		
	}

	public TelegramBot(FilmDatabase database, FilmRatingsDatabase ratingsDatabase, String username, String token, DefaultBotOptions options) {
		super(options);
		this.bot_username = username;
		this.bot_token = token;
		this.database = database;
		this.ratingsDatabase = ratingsDatabase;
		idTotalFieldMap = new HashMap<String, Map<Field, List<String>>>();
		idCurrentFieldMap = new HashMap<String, Field>();
		userDialogState = new HashMap<String, DialogState>();
	}

	private String processInput(String input, String username, String chatId) {
		User user = UserUtils.getUser(username, chatId);
		Dialog dialog = new Dialog(user, database, ratingsDatabase);
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

		Message inputMessage = update.getMessage();
		String inputCommand = inputMessage.getText();
		String id = inputMessage.getChatId().toString();
		String userFirstName = inputMessage.getFrom().getFirstName();
		SendMessage message = new SendMessage();
		
		if (userDialogState.get(id) == null)
			userDialogState.put(id,  DialogState.BASIC);
		
		DialogState state = userDialogState.get(id);

		System.out.println(userFirstName + ": " + inputCommand);

		State newState = getState(inputCommand, id, state);
		String answer = getAnswer(newState, userFirstName, id);
		idCurrentFieldMap.put(id, newState.currentField);
		
		userDialogState.put(id, newState.newState);

		message.setText(answer);
		message.setReplyMarkup(newState.getKeyboard());

		message.setChatId(inputMessage.getChatId());

		try {
			execute(message);
		} catch (TelegramApiException e) {
			// e.printStackTrace();
		}
	}
	
	public String getAnswer(State state, String username, String id) {
		return state.command == null ? state.answerString
				: processInput(state.command, username, id);
	}

	public State getState(String input, String chatId, DialogState state) {
		if (idTotalFieldMap.get(chatId) == null)
			idTotalFieldMap.put(chatId, new HashMap<Field, List<String>>());
		State currentState = new State(state, idTotalFieldMap.get(chatId), database, idCurrentFieldMap.get(chatId));
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
