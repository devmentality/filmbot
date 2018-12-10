//package telegram;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import storage.FileFilmHandler;
//import storage.FilmDatabase;
//import storage.TestFilmDatabaseFileHandler;
//import structures.Film;
//import utils.FilmUtils;
//
//public class TelegramBotTest {	
//	private FilmDatabase getDatabase() throws Exception {
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
//		FilmDatabase database = new FilmDatabase(
//				new FileFilmHandler(
//						new TestFilmDatabaseFileHandler(
//								FilmUtils.filmListToStringList(filmList))));
//	
//		return database;
//	}
//	
//	@Test
//	public void TwoUsersTestDifferentRequest() throws Exception {
//		FilmDatabase database = getDatabase();
//		TelegramBot bot = new TelegramBot(database, null, null);
//		State state1 = bot.getState("/y 1972", "1", DialogState.BASIC);
//		String ans1 = bot.getAnswer(state1, "name", "1");
//		State state2 = bot.getState("/c США", "2", DialogState.BASIC);
//		String ans2 = bot.getAnswer(state2, "name2", "2");
//		
//		assertEquals(ans1, "Крестный отец");
//		assertEquals(ans2, "Бойцовский клуб");		
//	}
//	
//	@Test
//	public void TwoUsersTestSameRequest() throws Exception {
//		FilmDatabase database = getDatabase();
//		TelegramBot bot = new TelegramBot(database, null, null);
//		State state1 = bot.getState("/y 1972", "1", DialogState.BASIC);
//		String ans1 = bot.getAnswer(state1, "name",  "1");
//		State state2 = bot.getState("/y 1972", "2", DialogState.BASIC);
//		String ans2 = bot.getAnswer(state2, "name2", "2");		
//	
//		assertEquals(ans1, "Крестный отец");
//		assertEquals(ans2, "Крестный отец");		
//	}
//	
//	@After
//	public void tearDown() {
//		tryToDeleteSavedFile();
//	}
//	
//	@Before
//	public void setUp() {
//		tryToDeleteSavedFile();
//	}
//	
//	private void tryToDeleteSavedFile() {
//		File userFile = new File("1" + ".csv");
//		File userFile2 = new File("2" + ".csv");
//		userFile.delete();
//		userFile2.delete();
//	}
// }
//
