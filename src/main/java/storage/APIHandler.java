package storage;

import java.util.ArrayList;
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

public class APIHandler {
	private TheMovieDbApi api;
	public Map<String, Integer> genresId;
	public List<String> years;

	public APIHandler(String apiKey) throws MovieDbException {
		api = new TheMovieDbApi(apiKey);
		genresId = getGenresId();
		years = getAvailiableYears();
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
		List<Film> possibleFilms = getFilmsByOptions(options);
		if (possibleFilms.size() == 0)
			return new Film("None", null);
		for (Film film : possibleFilms)
			if (!savedFilmsIDs.contains(film.ID))
				return film;
		return null;
	}

	private List<MovieBasic> getDiscoverResult(Map<Field, List<String>> commands) throws MovieDbException {
		Discover discover = new Discover();
		List<MovieBasic> discoveredFilms = new ArrayList<MovieBasic>();
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
					genres.add(genresId.get(genre).toString());
				discover.withGenres(String.join(",", genres));
			}
		}

		discoveredFilms = api.getDiscoverMovies(discover).getResults();
		return discoveredFilms;
	}

	private List<Film> getFilmsByOptions(Map<Field, List<String>> commands) throws MovieDbException {
		List<MovieBasic> discoveredFilms = getDiscoverResult(commands);
		List<Film> filmList = new ArrayList<Film>();
		if (discoveredFilms.size() == 0)
			return filmList;
		for (MovieBasic film : discoveredFilms) {
			Integer filmId = film.getId();
			if (commands.get(Field.YEAR) != null) {
				String year = api.getMovieInfo(filmId, "en").getReleaseDate().substring(0, 4);
				if (year.equals(commands.get(Field.YEAR).get(0)))
					filmList.add(new Film(filmId.toString(), film.getTitle()));
			}
			if (commands.get(Field.GENRE) != null)
				filmList.add(new Film(filmId.toString(), film.getTitle()));
		}
		return filmList;
	}
}
