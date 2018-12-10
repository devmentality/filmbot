//package storage;
//
//import static org.junit.Assert.assertEquals;
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
//import storage.FileFilmHandler;
//import structures.Field;
//import structures.Film;
//import utils.FilmUtils;
//
//public class FileFilmHandlerTest {
//
//	private static IFilmHandler filmHandler;
//	private static List<Film> filmList;
//	private static Film film1 = FilmUtils.getFilm("1", "Бойцовский клуб",
//			new ArrayList<String>(Arrays.asList(new String[] { "США", "Германия" })),
//			new ArrayList<String>(Arrays.asList(new String[] { "1999" })),
//			new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" })));
//	private static Film film2 = FilmUtils.getFilm("2", "Крестный отец",
//			new ArrayList<String>(Arrays.asList(new String[] { "США" })),
//			new ArrayList<String>(Arrays.asList(new String[] { "1972" })),
//			new ArrayList<String>(Arrays.asList(new String[] { "драма", "криминал" })));
//
//	@Before
//	public void setUp() throws Exception {
//		createFilmList();
//		filmHandler = new FileFilmHandler(new TestFilmDatabaseFileHandler(FilmUtils.filmListToStringList(filmList)));
//	}
//
//	private void createFilmList() {
//		filmList = new ArrayList<Film>();
//		filmList.add(FilmUtils.getFilm("ID", "Фильм", new ArrayList<String>(Arrays.asList(new String[] { "Страна" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "Год" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "Жанр" }))));
//		filmList.add(FilmUtils.getFilm("0", "Леон", new ArrayList<String>(Arrays.asList(new String[] { "Франция" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1994" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }))));
//		filmList.add(film1);
//		filmList.add(film2);
//		filmList.add(FilmUtils.getFilm("3", "Криминальное чтиво",
//				new ArrayList<String>(Arrays.asList(new String[] { "США" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1994" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "комедия", "криминал" }))));
//	}
//
//	@Test
//	public void testGetFilmsCount() {
//		int filmsCount = filmHandler.getFilmsCount();
//		assertEquals(4, filmsCount);
//	}
//
//	@Test
//	public void testGetFilmsByOptions() {
//		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
//		options.put(Field.GENRE, new ArrayList<String>());
//		options.get(Field.GENRE).add("драма");
//		List<Film> filmsByOptions = filmHandler.getFilmsByOptions(options);
//		assertEquals(2, filmsByOptions.size());
//		assertEquals(true, filmsByOptions.contains(film1));
//		assertEquals(true, filmsByOptions.contains(film2));
//	}
//
////	@Test
////	public void testGetAvaliableFieldValues() {
////		String[] fieldValues = filmHandler.getAvaliableFieldValues(Field.COUNTRY);
////		String[] expectedData = { "Германия", "США", "Франция" };
////		for (int i = 0; i < fieldValues.length; i++)
////			assertEquals(expectedData[i], fieldValues[i]);
////	}
//
//}
