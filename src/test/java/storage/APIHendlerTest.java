package storage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.omertron.themoviedbapi.MovieDbException;

import structures.Field;
import structures.Film;

public class APIHendlerTest {

	private APIHandler apiDatabase = mock(APIHandler.class);
	private List<String> ids;
	private Map<Field, List<String>> options;

	@Before
	public void setUp() {
		ids = new ArrayList<String>();
		ids.add("1");
		ids.add("2");
		ids.add("3");
		options = new HashMap<Field, List<String>>();
		options.put(Field.GENRE, new ArrayList<String>());
		options.get(Field.GENRE).add("Thriller");
		options.put(Field.YEAR, new ArrayList<String>());
		options.get(Field.YEAR).add("1999");
	}

	@Test
	public void testGetFilm() throws IOException, MovieDbException {
		Film film = new Film("2", "Fight Club");
		when(apiDatabase.getFilm(options, ids)).thenReturn(film);
		assertEquals(film, apiDatabase.getFilm(options, ids));
	}

}
