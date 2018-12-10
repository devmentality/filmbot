package dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omertron.themoviedbapi.MovieDbException;

import storage.APIHandler;
import structures.Film;
import structures.Field;
import structures.User;

public class Dialog {

	private User user;
	private APIHandler apiDatabase;

	public Dialog(User user, APIHandler apiDatabase) throws MovieDbException {
		this.user = user;
		this.apiDatabase = apiDatabase;
	}

	public String startDialog() {
		if (user.firstTime)
			return String.format("Добро пожаловать, %s.%s", user.name, Phrases.HELP);
		return String.format("Давно не виделись, %s.", user.name);
	}

	public String processInput(String input) throws MovieDbException {
		if (input.length() < 3)
			return Phrases.SHORT_COMMAND;

		if (input.equals("/help"))
			return Phrases.HELP;

		if (input.equals("/genres"))
			return Phrases.AVAILAIBLE_GENRES + String.join("\n", apiDatabase.genresId.keySet());

		if (input.equals("/years"))
			return Phrases.AVAILAIBLE_YEARS;

		if (input.equals("/next")) {
			if (user.currentOptions == null)
				return Phrases.NEXT_WITHOUT_OPT;
			return getFilm(user.currentOptions);
		}
		
		return processGetFilmCommand(input);
	}
	
	private String processGetFilmCommand(String input) throws MovieDbException {
		String[] commandArray = input.trim().substring(1).split("/");

		Map<Field, List<String>> commands = new HashMap<Field, List<String>>();

		for (int i = 0; i < commandArray.length; i++) {
			String[] options = commandArray[i].split(" ", 2);
			if (options.length % 2 != 0)
				return Phrases.UNKNOWN_COMMAND;
			String fieldShortCut = options[0].trim();
			String requestedOption = options[1].trim();

			boolean knownField = false;

			for (Field field : Field.values()) {

				if (!fieldShortCut.equals(field.shortCut()))
					continue;

//				if (!database.requestExistInDatabase(field, requestedOption)) {
//					user.clearCurrentOptions();
//					return field.noFilmsAtAll();
//				}

				if (commands.get(field) == null)
					commands.put(field, new ArrayList<String>());

				commands.get(field).add(requestedOption);
				knownField = true;
			}

			if (!knownField)
				return Phrases.UNKNOWN_COMMAND;
		}
		return getFilm(commands);
	}

	private String getFilm(Map<Field, List<String>> commands) throws MovieDbException {
		user.changeCurrentOptions(commands);
		Film film = apiDatabase.getFilm(commands, user.savedFilmsIDs);
		if (film != null && film.ID.equals("None"))
			return Phrases.NO_SUCH_FILM;
		else if (film != null)
			user.addFilm(film);
		else
			user.clearCurrentOptions();
		return film != null ? film.title : Phrases.NO_MORE_FILM;
	}

}