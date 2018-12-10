//package dialog;
//
//import storage.FileFilmHandler;
//import storage.FilmDatabase;
//import storage.TestFilmDatabaseFileHandler;
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
//import dialog.Dialog;
//import dialog.Phrases;
//import structures.User;
//import structures.Film;
//import structures.Field;
//import utils.FilmUtils;
//
//import static org.junit.Assert.assertEquals;
//
//public class DialogTest {
//	private User user;
//	private Dialog dialog;
//	private FilmDatabase database;
//
//	@Before
//	public void setUp() throws Exception {
//		List<Film> filmList = new ArrayList<Film>();
//		filmList.add(FilmUtils.getFilm("ID", "Фильм", new ArrayList<String>(Arrays.asList(new String[] { "Страна" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "Год" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "Жанр" }))));
//		filmList.add(FilmUtils.getFilm("8", "Бойцовский клуб",
//				new ArrayList<String>(Arrays.asList(new String[] { "США", "Германия" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1999" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }))));
//		filmList.add(FilmUtils.getFilm("5", "Леон", new ArrayList<String>(Arrays.asList(new String[] { "Франция" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1994" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }))));
//		filmList.add(FilmUtils.getFilm("13", "Криминальное чтиво",
//				new ArrayList<String>(Arrays.asList(new String[] { "США" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1994" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "комедия", "криминал" }))));
//		filmList.add(
//				FilmUtils.getFilm("12", "Крестный отец", new ArrayList<String>(Arrays.asList(new String[] { "США" })),
//						new ArrayList<String>(Arrays.asList(new String[] { "1972" })),
//						new ArrayList<String>(Arrays.asList(new String[] { "драма", "криминал" }))));
//		List<String> userList = new ArrayList<String>();
//		userList.add("12");
//		user = new User("name", "name", userList, null);
//		database = new FilmDatabase(
//				new FileFilmHandler(new TestFilmDatabaseFileHandler(FilmUtils.filmListToStringList(filmList))));
//		dialog = new Dialog(user, database);
//	}
//
//	@Test
//	public void testStartDialogFirstTime() throws Exception {
//		user = new User("name", "name", null, null);
//		dialog = new Dialog(user, database);
//		assertEquals(String.format("Добро пожаловать, name.%s", Phrases.HELP), dialog.startDialog());
//	}
//
//	@Test
//	public void testStartDialogNotFirstTime() {
//		assertEquals("Давно не виделись, name.", dialog.startDialog());
//	}
//
//	@Test
//	public void testProcessInputHelp() {
//		assertEquals(Phrases.HELP, dialog.processInput("/help"));
//	}
//
//	@Test
//	public void testProcessInputShort() {
//		assertEquals(Phrases.SHORT_COMMAND, dialog.processInput("c:"));
//	}
//
//	@Test
//	public void testGetCountry() {
//		assertEquals("Бойцовский клуб", dialog.processInput("/c США"));
//	}
//
//	@Test
//	public void testGetNextCountry() {
//		assertEquals("Бойцовский клуб", dialog.processInput("/c США"));
//		assertEquals("Криминальное чтиво", dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testGetYear() {
//		assertEquals("Леон", dialog.processInput("/y 1994"));
//	}
//
//	@Test
//	public void testGetNextYear() {
//		assertEquals("Леон", dialog.processInput("/y 1994"));
//		assertEquals("Криминальное чтиво", dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testGetGenre() {
//		assertEquals("Бойцовский клуб", dialog.processInput("/g триллер"));
//	}
//
//	@Test
//	public void testGetNextGenre() {
//		assertEquals("Бойцовский клуб", dialog.processInput("/g триллер"));
//		assertEquals("Леон", dialog.processInput("/g триллер"));
//	}
//
//	@Test
//	public void testUnknownCommand() {
//		assertEquals(Phrases.UNKNOWN_COMMAND, dialog.processInput("/p lol"));
//	}
//
//	@Test
//	public void testGetYearWhichNotInFilmList() {
//		assertEquals(Field.YEAR.noFilmsAtAll(), dialog.processInput("/y 900"));
//	}
//
//	@Test
//	public void testGetGenreWhichNotInFilmList() {
//		assertEquals(Field.GENRE.noFilmsAtAll(), dialog.processInput("/g телепузик"));
//	}
//
//	@Test
//	public void testNextWithoutOption() {
//		assertEquals(Phrases.NEXT_WITHOUT_OPT, dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testNoNextFilmYear() {
//		assertEquals("Бойцовский клуб", dialog.processInput("/y 1999"));
//		assertEquals(Phrases.NO_MORE_FILM, dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testNoNextFilmCountry() {
//		assertEquals("Леон", dialog.processInput("/c Франция"));
//		assertEquals(Phrases.NO_MORE_FILM, dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testNoNextFilmGenre() {
//		assertEquals("Криминальное чтиво", dialog.processInput("/g комедия"));
//		assertEquals(Phrases.NO_MORE_FILM, dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testsUserSeenFilmYear() {
//		assertEquals(Phrases.NO_MORE_FILM, dialog.processInput("/y 1972"));
//	}
//
//	@Test
//	public void testYearAndGenre() {
//		assertEquals("Криминальное чтиво", dialog.processInput("/y 1994 /g комедия"));
//	}
//
//	@Test
//	public void testYearAndCountry() {
//		assertEquals("Леон", dialog.processInput("/c Франция /y 1994"));
//	}
//
//	@Test
//	public void testGenreAndCountry() {
//		assertEquals("Леон", dialog.processInput("/g триллер /c Франция"));
//	}
//
//	@Test
//	public void testTwoGenres() {
//		assertEquals("Криминальное чтиво", dialog.processInput("/g криминал /g комедия"));
//	}
//
//	@Test
//	public void testTwoYears() {
//		assertEquals(Phrases.NO_SUCH_FILM, dialog.processInput("/y 1999 /y 1994"));
//	}
//
//	@Test
//	public void testsUserNextWithCurrentField() {
//		List<String> userList = new ArrayList<String>();
//		userList.add("5");
//		Map<Field, List<String>> userData = new HashMap<Field, List<String>>();
//		userData.put(Field.YEAR, new ArrayList<String>());
//		userData.get(Field.YEAR).add("1994");
//		user = new User("name", "name", userList, userData);
//		dialog = new Dialog(user, database);
//		assertEquals("Криминальное чтиво", dialog.processInput("/next"));
//	}
//
//}