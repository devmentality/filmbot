//package telegram;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//
//import structures.Field;
//import structures.Film;
//import utils.FilmUtils;
//
//public class TelegramBotTest {
//	
//	private List<Film> getFilms() {
//		List<Film> filmList = new ArrayList<Film>();
//		filmList.add(FilmUtils.getFilm("8", "Бойцовский клуб", "США, Германия", "1999", "триллер, драма, криминал"));
//		filmList.add(FilmUtils.getFilm("5", "Леон", "Франция", "1994", "триллер, драма, криминал"));
//		filmList.add(FilmUtils.getFilm("13", "Криминальное чтиво", "США", "1994", "триллер, комедия, криминал"));
//		filmList.add(FilmUtils.getFilm("12", "Крестный отец", "США", "1972", "драма, криминал"));
//		return filmList;
//	}
//	
//	@Test
//	public void TwoUsersTestDifferentRequest() {
//		Map<Field, Map<String, List<Film>>> filmMapsByField = FilmUtils.getFilmMapsByField(getFilms());
//		TelegramBot bot = new TelegramBot(filmMapsByField, null, null);
//		String ans1 = bot.getAnswer("/y 1972", "1", "name");
//		String ans2 = bot.getAnswer("/c США", "2", "name2");
//		
//		assertEquals(ans1, "Крестный отец");
//		assertEquals(ans2, "Бойцовский клуб");		
//	}
//	
//	@Test
//	public void TwoUsersTestSameRequest() {
//		Map<Field, Map<String, List<Film>>> filmMapsByField = FilmUtils.getFilmMapsByField(getFilms());
//		TelegramBot bot = new TelegramBot(filmMapsByField, null, null);
//		String ans1 = bot.getAnswer("/y 1972", "1", "name");
//		String ans2 = bot.getAnswer("/y 1972", "2", "name2");
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
//
//}
