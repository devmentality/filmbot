//package storage;
//
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import storage.FilmDatabase;
//import structures.Field;
//import structures.Film;
//import utils.FilmUtils;
//
//public class FilmDatabaseTest {
//
//	private List<Film> filmList;
//	private FilmDatabase filmDatabase;
//	private IFilmDatabaseFileHandler databaseHandler;
//	private IFilmHandler fileFilmHandler;
//	private static Film film2 = FilmUtils.getFilm("2", "Крестный отец",
//			new ArrayList<String>(Arrays.asList(new String[] { "США" })),
//			new ArrayList<String>(Arrays.asList(new String[] { "1972" })),
//			new ArrayList<String>(Arrays.asList(new String[] { "драма", "криминал" })));
//
//	private void createFilmList() {
//		filmList = new ArrayList<Film>();
//		filmList.add(FilmUtils.getFilm("ID", "Фильм", new ArrayList<String>(Arrays.asList(new String[] { "Страна" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "Год" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "Жанр" }))));
//		filmList.add(FilmUtils.getFilm("0", "Леон", new ArrayList<String>(Arrays.asList(new String[] { "Франция" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1994" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }))));
//		filmList.add(FilmUtils.getFilm("1", "Бойцовский клуб",
//				new ArrayList<String>(Arrays.asList(new String[] { "США", "Германия" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1999" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }))));
//		filmList.add(film2);
//		filmList.add(FilmUtils.getFilm("3", "Криминальное чтиво",
//				new ArrayList<String>(Arrays.asList(new String[] { "США" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1994" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "комедия", "криминал" }))));
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		createFilmList();
//		databaseHandler = new TestFilmDatabaseFileHandler(FilmUtils.filmListToStringList(filmList));
//		fileFilmHandler = new FileFilmHandler(databaseHandler);
//		filmDatabase = new FilmDatabase(fileFilmHandler);
//	}
//
//	@Test
//	public void testGetFilm() {
//		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
//		options.put(Field.GENRE, new ArrayList<String>());
//		options.get(Field.GENRE).add("драма");
//		List<String> idList = new ArrayList<String>();
//		idList.add("1");
//		Film film = filmDatabase.getFilm(options, idList);
//		assertEquals(film2, film);
//	}
//
//	@Test
//	public void testrequestExistInDatabase() throws Exception {
//		assertTrue(filmDatabase.requestExistInDatabase(Field.GENRE, "комедия"));
//		assertTrue(filmDatabase.requestExistInDatabase(Field.GENRE, "триллер"));
//		assertTrue(filmDatabase.requestExistInDatabase(Field.YEAR, "1972"));
//		assertTrue(filmDatabase.requestExistInDatabase(Field.YEAR, "1994"));
//	}
//
//}
