package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;

import structures.Field;
import structures.Film;
import utils.RatingCalculator;
import structures.RatedFilm;
import structures.Vote;

public class APIHandler {
	private TheMovieDbApi api;
	public Map<String, Integer> genresId;
	public List<String> years;
	public VotesDatabase votesDatabase;

	public APIHandler(String apiKey, VotesDatabase votesDatabase) throws MovieDbException {
		api = new TheMovieDbApi(apiKey);
		genresId = getGenresId();
		years = getAvailiableYears();
		this.votesDatabase = votesDatabase;
	}

	private Map<String, Integer> getGenresId() throws MovieDbException {
		List<Genre> genres = api.getGenreMovieList("en").getResults();
		Map<String, Integer> genresId = new HashMap<String, Integer>();
		for (int i = 0; i < genres.size(); i++)
			genresId.put(genres.get(i).getName(), genres.get(i).getId());
		return genresId;
	}

	private List<String> getAvailiableYears() throws MovieDbException {
		int maxYear = 2019;
		int minYear = 1900;
		List<String> years = new ArrayList<String>();
		for (int i = 0; i < maxYear - minYear + 1; i++)
			years.add(Integer.toString(minYear + i));
		return years;
	}

	public Film getFilm(Map<Field, List<String>> options, List<String> savedFilmsIDs) throws MovieDbException {
		List<RatedFilm> possibleFilms = getFilmsByOptions(options);
		Collections.sort(possibleFilms, Comparator.reverseOrder());

		for (RatedFilm ratedFilm : possibleFilms)
			if (!savedFilmsIDs.contains(ratedFilm.getFilm().ID))
				return ratedFilm.getFilm();
		return null;
	}

	private List<MovieBasic> getDiscoverResult(Map<Field, List<String>> commands) throws MovieDbException {
		Discover discover = new Discover();
		List<MovieBasic> discoveredFilms = new ArrayList<MovieBasic>();
		if (commands.containsKey(Field.YEAR) && commands.get(Field.YEAR).size() > 1)
			return discoveredFilms;
		for (Field field : commands.keySet()) {
			try {
				if (field == Field.YEAR)
					discover.year(Integer.parseInt(commands.get(field).get(0)));
			} catch (NumberFormatException e) {
				return discoveredFilms;
			}
			if (field == Field.GENRE) {
				List<String> genres = new ArrayList<String>();
				for (String genre : commands.get(field))
				{
					if (!genresId.containsKey(genre))
						return new ArrayList<MovieBasic>();
					genres.add(genresId.get(genre).toString());
				}
				discover.withGenres(String.join(",", genres));
			}
		}

		discoveredFilms = api.getDiscoverMovies(discover).getResults();
		return discoveredFilms;
	}

	private List<RatedFilm> getFilmsByOptions(Map<Field, List<String>> commands) throws MovieDbException {
		List<Vote> votes = new ArrayList<Vote>();
		try {
			votes = votesDatabase.getAllVotes();
		} catch (IOException ex) {
		}
		List<MovieBasic> discoveredFilms = getDiscoverResult(commands);
		List<RatedFilm> filmList = new ArrayList<RatedFilm>();
		if (discoveredFilms.size() == 0)
			return filmList;
		for (MovieBasic film : discoveredFilms) {
			Integer filmId = film.getId();
			if (commands.get(Field.YEAR) != null) {
				String year = api.getMovieInfo(filmId, "en").getReleaseDate().substring(0, 4);
				if (year.equals(commands.get(Field.YEAR).get(0)))
					filmList.add(new RatedFilm(new Film(filmId.toString(), film.getTitle()),
							RatingCalculator.CalculateRating(votes, film.getTitle())));
			}
			if (commands.get(Field.GENRE) != null)
				filmList.add(new RatedFilm(new Film(filmId.toString(), film.getTitle()),
						RatingCalculator.CalculateRating(votes, film.getTitle())));
		}
		return filmList;
	}
}
