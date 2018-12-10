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

	public APIHandler(String apiKey) throws MovieDbException {
		api = new TheMovieDbApi("f6b6d91dd2768c08788768a2e8662e35");
		genresId = getGenresId();
	}

	public Map<String, Integer> getGenresId() throws MovieDbException {
		api = new TheMovieDbApi("f6b6d91dd2768c08788768a2e8662e35");
		List<Genre> genres = api.getGenreMovieList("en").getResults();
		Map<String, Integer> genresId = new HashMap<String, Integer>();
		for (int i = 0; i < genres.size(); i++)
			genresId.put(genres.get(i).getName(), genres.get(i).getId());
		return genresId;
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

	public List<MovieBasic> getDiscoverResult(Map<Field, List<String>> commands) throws MovieDbException {
		Discover discover = new Discover();
		List<MovieBasic> discoveredFilms = new ArrayList<MovieBasic>();
		for (Field field : commands.keySet()) {
			if (field == Field.YEAR)
				discover.year(Integer.parseInt(commands.get(field).get(0)));
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

	public List<Film> getFilmsByOptions(Map<Field, List<String>> commands) throws MovieDbException {
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
