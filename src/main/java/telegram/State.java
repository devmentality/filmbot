package telegram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.omertron.themoviedbapi.MovieDbException;

import storage.APIHandler;
import structures.Field;

public class State {

	private DialogState currentState;
	public DialogState newState;
	private Map<Field, List<String>> currentIdFieldMap;
	private APIHandler apiDatabase;

	private ReplyKeyboardMarkup keyboard;

	public String command = null;
	public String answerString;
	public Field currentField;
	
	private Map<String, String> statelessCommands;

	public State(DialogState state, Map<Field, List<String>> currentIdFieldMap, APIHandler apiDatabase,
			Field currentField) throws MovieDbException {
		this.currentState = state;
		this.currentIdFieldMap = currentIdFieldMap;
		this.apiDatabase = apiDatabase;
		this.currentField = currentField;
		
		statelessCommands = new HashMap<String, String>();
		statelessCommands.put("Рейтинг фильмов", "/filmsRating");
		statelessCommands.put("Лайкающие", "/userLikes");
		statelessCommands.put("Критиканты", "/userDislikes");
		statelessCommands.put("Активные за день", "/topToday");
		statelessCommands.put("За 3 дня", "/top3days");
		statelessCommands.put("За неделю", "/topWeek");
	}

	public void processInput(String input) {
		if ("NEXT".equals(input)) {
			command = "/next";
			currentField = null;
			for (Field field : Field.values())
				currentIdFieldMap.remove(field);
			newState = DialogState.BASIC;
			keyboard = getBasicKeyboard();
			return;
		}
		if (statelessCommands.containsKey(input))
		{
			command = statelessCommands.get(input);
			newState = currentState;
			keyboard = getCurrentStateKeyboard();
			return;
		}

		switch (currentState) 
		{
			case BASIC:
				processBasicState(input);
				break;
			case CHOSING:
				processChosingState(input);
				break;
			case MORE_OPTIONS:
				processMoreOptionsState(input);
				break;
		}
	}
	
	private ReplyKeyboardMarkup getCurrentStateKeyboard()
	{
		switch (currentState) 
		{
			case CHOSING:
				return getChosingKeyboard(currentField);
			case MORE_OPTIONS:
				return getMoreOptionsKeyboard();
			default:
				return getBasicKeyboard();
		}
	}

	private void processMoreOptionsState(String input) {
		if ("ЕЩЕ ОПЦИЯ".equals(input)) {
			keyboard = getBasicKeyboard();
			newState = DialogState.BASIC;
			answerString = "Выберите еще опцию";
		} else if ("ПОЛУЧИТЬ ФИЛЬМ".equals(input)) {
			keyboard = getBasicKeyboard();
			newState = DialogState.BASIC;
			currentField = null;
			command = getCommand(currentIdFieldMap);
			for (Field field : Field.values())
				currentIdFieldMap.remove(field);
		} else {
			command = input;
			keyboard = getBasicKeyboard();
			newState = DialogState.BASIC;
			currentField = null;
		}
	}

	private void processChosingState(String input) {
			currentIdFieldMap.get(currentField).add(input);
			newState = DialogState.MORE_OPTIONS;
			answerString = "Есть еще параметры?";
			currentField = null;
			keyboard = getMoreOptionsKeyboard();
	}

	private void processBasicState(String input) {
		if (!Arrays.toString(Field.values()).contains(input)) {
			command = input;
			keyboard = getBasicKeyboard();
			newState = DialogState.BASIC;
			currentField = null;
		} else {
			Field field = Field.valueOf(input);
			if (currentIdFieldMap.get(field) == null)
				currentIdFieldMap.put(field, new ArrayList<String>());
			currentField = field;
			newState = DialogState.CHOSING;
			answerString = field.nowChoose();
			keyboard = getChosingKeyboard(field);
		}
	}

	public State getNewState() throws MovieDbException {
		return new State(newState, currentIdFieldMap, apiDatabase, currentField);
	}

	private ReplyKeyboardMarkup getChosingKeyboard(Field field) {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		List<String> buttons = new ArrayList<String>();
		if (field.equals(Field.GENRE)) {
			buttons.addAll(apiDatabase.genresId.keySet());
		}
		if (field.equals(Field.YEAR))
			buttons = apiDatabase.years;
		for (String button : buttons) {
			KeyboardRow row = new KeyboardRow();
			row.add(button);
			keyboard.add(row);
		}
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;

	}

	private ReplyKeyboardMarkup getMoreOptionsKeyboard() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();
		row.add("ЕЩЕ ОПЦИЯ");
		keyboard.add(row);
		row = new KeyboardRow();
		row.add("ПОЛУЧИТЬ ФИЛЬМ");
		keyboard.add(row);
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;

	}

	private ReplyKeyboardMarkup getBasicKeyboard() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();
		row.add("YEAR");
		keyboard.add(row);
		row = new KeyboardRow();
		row.add("GENRE");
		keyboard.add(row);
		row = new KeyboardRow();
		row.add("NEXT");
		keyboard.add(row);
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;
	}

	public ReplyKeyboardMarkup getKeyboard() {
		return keyboard;
	}
	
	private String getCommand(Map<Field, List<String>> inputMap) {
		String command = "";
		for (Map.Entry<Field, List<String>> entry : inputMap.entrySet()) {
			command += "/" + entry.getKey().shortCut() + " " + String.join(", ", entry.getValue()) + " ";
		}
		return command;
	}

}