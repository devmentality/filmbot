package dialog;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import structures.Film;
import structures.Field;
import structures.User;
import utils.FilmUtils;

public class Dialog {

	private User user;
	private List<Film> filmList;
	private Map<Field, Map<String, List<Film>>> filmMapsByField;

	public Dialog(User user, List<Film> filmList) {
		this.user = user;
		this.filmList = filmList;
		this.filmMapsByField = FilmUtils.getFilmMapsByField(filmList);
	}

	public String startDialog() {
		if (user.firstTime)
			return String.format("Добро пожаловать, %s.%s", user.name, Phrases.HELP);
		return String.format("Давно не виделись, %s.", user.name);
	}

	public String processInput(String input) {
		if (input.equals("/help"))
			return Phrases.HELP;

		if (input.equals("/countries"))
			return Phrases.AVAILAIBLE_COUNTRIES;

		if (input.equals("/genres"))
			return Phrases.AVAILAIBLE_GENRES;

		if (input.equals("/years"))
			return Phrases.AVAILAIBLE_YEARS;

		if (input.equals("/add"))
			return "";

//		if (input.equals("/next")) {
//			if (user.currentField == null)
//				return Phrases.NEXT_WITHOUT_OPT;
//
//			return getFilm(user.currentField, user.currentOptions.get(user.currentField));
//		}

		if (input.length() < 3)
			return Phrases.SHORT_COMMAND;
		// наверное, это не так уж и важно или был отладочный вывод
		// System.out.println(input);
		Scanner scan = new Scanner(System.in);
		PrintStream printStream = new PrintStream(System.out);

		Map<Field, List<String>> commands = new HashMap<Field, List<String>>();
		while (true) {
			String[] command = input.trim().split("\\s", 2);
			for (Field field : Field.values())
				if (commands.keySet().contains(field) && command[0].equals(field.shortCut())) {
					commands.get(field).add(command[1]);
				} else {
					commands.put(field, new ArrayList<String>());
				}

			printStream.println("Дополнительный параметр?[да/нет]");
			String answer = scan.nextLine();
			if (answer.equals("да"))
				input = scan.nextLine();
			else if (answer.equals("нет"))
				break;
			else
				return Phrases.UNKNOWN_COMMAND;
		}
		scan.close();
		return getFilm(commands);
//		for (Field field : Field.values()) {
//			if (command.equals(field.shortCut())) {
//				return getFilm(field, request);
//			}
//		}return Phrases.UNKNOWN_COMMAND;

	}

//эта штука должна работать как по одному параметру, так и по нескольким
	private List<Film> getFilmsManyOptions(Map<Field, List<String>> commands) {
		List<Film> correctFilms = new ArrayList<Film>();
		outerloop: for (Film film : filmList) {
			for (Field field : commands.keySet())
				for (String parameter : commands.get(field))
					if (film.getField(field).contains(parameter))
						continue;
					else
						continue outerloop;
			correctFilms.add(film);
		}
		return correctFilms;
	}

	private String getFilm(Map<Field, List<String>> commands) {
		List<Film> filmsManyOptions = getFilmsManyOptions(commands);
//		Map<String, List<Film>> filmsDict = filmMapsByField.get(field);

//		if (!filmsDict.containsKey(key)) {
//			user.clearCurrentField();
//			return field.noFilmsAtAll();
//		}
//там в юзере надо будет ещё поменять что-то, но я запуталась
//		user.changeCurrentOption(field, key);

		String film = getUnshownFilm(filmsManyOptions);
		return film != null ? film : Phrases.NO_SUCH_FILM;
	}

	private String getUnshownFilm(List<Film> films) {
		for (Film film : films) {
			if (user.savedFilmsIDs.contains(film.ID))
				continue;
			user.addFilm(film);
			return film.title;
		}
		return null;
	}
}