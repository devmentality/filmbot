package dialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import storage.FilmDatabase;
import storage.FilmRatingsDatabase;
import structures.Film;
import structures.Field;
import structures.User;

public class Dialog {

	private User user;
	private FilmDatabase database;
	private FilmRatingsDatabase ratingsDatabase;

	public Dialog(User user, FilmDatabase database, FilmRatingsDatabase ratingsDatabase) {
		this.user = user;
		this.database = database;
		this.ratingsDatabase = ratingsDatabase;
	}

	public String startDialog() {
		if (user.firstTime)
			return String.format("Добро пожаловать, %s.%s", user.name, Phrases.HELP);
		return String.format("Давно не виделись, %s.", user.name);
	}

	public String processInput(String input) {
		if (input.length() < 3)
			return Phrases.SHORT_COMMAND;

		if (input.equals("/help"))
			return Phrases.HELP;

		if (input.equals("/countries"))
			return Phrases.AVAILAIBLE_COUNTRIES + String.join("\n", database.getFieldValuesArray(Field.COUNTRY));

		if (input.equals("/genres"))
			return Phrases.AVAILAIBLE_GENRES + String.join("\n", database.getFieldValuesArray(Field.GENRE));

		if (input.equals("/years"))
			return Phrases.AVAILAIBLE_YEARS + String.join("\n", database.getFieldValuesArray(Field.YEAR));

		if (input.equals("/adding"))
			return Phrases.ADDING_PROCESS;

		if (input.equals("/next")) {
			if (user.currentOptions == null)
				return Phrases.NEXT_WITHOUT_OPT;
			return getFilm(user.currentOptions);
		}
		
		if (input.trim().startsWith("/like"))
			return processVote(input, true);
		
		if (input.trim().startsWith("/dislike"))
			return processVote(input, false);
		
		if (input.trim().startsWith("/add")) 
			return processAdd(input);
		
		return processGetFilmCommand(input);
	}
	
	private String processVote(String input, boolean like)
	{
		String trimmedInput = input.trim();
		int delimiterIndex = trimmedInput.indexOf(' ');
		if (delimiterIndex == -1)
			return "Некорректная операция";
		
		String filmName = trimmedInput.substring(delimiterIndex + 1, trimmedInput.length());
		
		try
		{
			if (!ratingsDatabase.contains(filmName))
				ratingsDatabase.addFilm(filmName);
			ratingsDatabase.updateRating(filmName, like ? 1 : -1);
		}
		catch(IOException ex)
		{
			return "Извините, у нас проблемы";
		}
		
		return "Ваш голос учтен!";
	}
	
	private String processGetFilmCommand(String input) {
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

				if (!database.requestExistInDatabase(field, requestedOption)) {
					user.clearCurrentOptions();
					return field.noFilmsAtAll();
				}

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
	
	private String processAdd(String input) {
		String title = "";
		List<String> countries = new ArrayList<String>();
		List<String> year = new ArrayList<String>();
		List<String> genres = new ArrayList<String>();

		String[] commandArray = input.trim().substring(4).split("/");

		for (int i = 0; i < commandArray.length; i++) {
			String[] options = commandArray[i].split(" ", 2);
			if (options.length < 2)
				return Phrases.ADDING_PROCESS_ERROR;
			String command = options[0].trim();
			String option = options[1].trim();

			switch (command) {
			case "t":
				title = option;
				break;
			case "c":
				countries.addAll(Arrays.asList(option.split(", ")));
				break;
			case "y":
				year.add(option);
				break;
			case "g":
				genres.addAll(Arrays.asList(option.split(", ")));
				break;
			}
		}
		if (title == "" || countries.size() == 0 || year.size() == 0 || genres.size() == 0)
			return Phrases.ADDING_PROCESS_ERROR;
		try {
			database.addFilmToDatabase(title, countries, year, genres);
		} catch (Exception e) {
			return e.getMessage();
		}
		return Phrases.ADDING_FILM;
	}

	private String getFilm(Map<Field, List<String>> commands) {
		user.changeCurrentOptions(commands);

		Film film = database.getFilm(commands, user.savedFilmsIDs);
		if (film != null && film.ID.equals("None"))
			return Phrases.NO_SUCH_FILM;
		else if (film != null)
			user.addFilm(film);
		else
			user.clearCurrentOptions();
		if (film == null)
			return Phrases.NO_MORE_FILM;
		
		int rating;
		try
		{
			rating = ratingsDatabase.getRating(film.title);
			return String.format("%s\nРейтинг: %d", film.title, rating); 
		}
		catch(IOException ex)
		{
			return film.title; 
		}
	}

}