package dialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omertron.themoviedbapi.MovieDbException;
import storage.APIHandler;
import storage.VotesDatabase;

import structures.Film;
import structures.Field;
import structures.User;
import structures.Vote;
import utils.RatingCalculator;

public class Dialog {

	private User user;
	private APIHandler apiDatabase;
	private VotesDatabase votesDatabase;

	public Dialog(User user, APIHandler apiDatabase, VotesDatabase votesDatabase) throws MovieDbException {
		this.user = user;
		this.apiDatabase = apiDatabase;
		this.votesDatabase = votesDatabase;
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

		if (input.trim().startsWith("/like") || input.trim().startsWith("/dislike")) {
			Vote vote;
			try {
				vote = ParseVote(user, input);
			} catch (IllegalArgumentException ex) {
				return ex.getMessage();
			}
			return processVote(vote);
		}

		return processGetFilmCommand(input);
	}

	private Vote ParseVote(User user, String input) throws IllegalArgumentException {
		String trimmedInput = input.trim();
		int delimiterIndex = trimmedInput.indexOf(' ');
		if (delimiterIndex == -1)
			throw new IllegalArgumentException("Некорректный формат");

		String filmName = trimmedInput.substring(delimiterIndex + 1, trimmedInput.length());

		return new Vote(user.ID, filmName, input.startsWith("/like"));
	}

	private String processVote(Vote vote) {
		try {
			if (votesDatabase.containsVote(vote.getUserId(), vote.getFilmName()))
				return "Вы уже оставили свой голос за этот фильм.";
			votesDatabase.addVote(vote);
			return "Ваш голос учтён!";
		} catch (IOException ex) {
			return "Извините, у нас проблемы";
		}
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
		if (film != null)
			user.addFilm(film);
		else
			user.clearCurrentOptions();
		if (film == null)
			return Phrases.NO_SUCH_FILM;

		int rating;
		try {
			rating = RatingCalculator.CalculateRating(votesDatabase.getVotes(film.title), film.title);
			return String.format("%s\nРейтинг: %d", film.title, rating);
		} catch (IOException ex) {
			return film.title;
		}
	}

}