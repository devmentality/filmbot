//package structures;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//
//import structures.User;
//import utils.FilmUtils;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class UserTest {
//
//	@Test
//	public void testCreateUserFirstEntry() {
//		User user = new User("Даша", "Даша", null, null);
//
//		assertEquals("Даша", user.name);
//		assertEquals(new ArrayList<String>(), user.savedFilmsIDs);
//	}
//
//	@Test
//	public void testCreateUserSecondEntry() {
//		List<String> IDList = new ArrayList<String>();
//		IDList.add("8");
//		User user = new User("Даша", "Даша", IDList, null);
//
//		assertEquals("Даша", user.name);
//		assertEquals("8", user.savedFilmsIDs.get(0));
//		assertEquals(1, user.savedFilmsIDs.size());
//	}
//
//	@Test
//	public void testCreateUserEntryWithField() {
//		List<String> IDList = new ArrayList<String>();
//		IDList.add("8");
//		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
//		User user = new User("Даша", "Даша", IDList, options);
//
//		assertEquals("Даша", user.name);
//		assertEquals("8", user.savedFilmsIDs.get(0));
//		assertEquals(1, user.savedFilmsIDs.size());
//		assertEquals(null, user.currentOptions.get(Field.YEAR));
//	}
//
//	@Test
//	public void testAddFilm() {
//		List<String> IDList = new ArrayList<String>();
//		IDList.add("8");
//		User user = new User("Даша", "Даша", IDList, null);
//
//		user.addFilm(FilmUtils.getFilm("13", "Криминальное чтиво",
//				new ArrayList<String>(Arrays.asList(new String[] { "США" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1994" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "комедия", "криминал" }))));
//
//		assertEquals("8", user.savedFilmsIDs.get(0));
//		assertEquals("13", user.savedFilmsIDs.get(1));
//		assertEquals(2, user.savedFilmsIDs.size());
//	}
//
//	@Test
//	public void testClearCurrentField() {
//		List<String> IDList = new ArrayList<String>();
//		IDList.add("8");
//		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
//		User user = new User("Даша", "Даша", IDList, options);
//		user.clearCurrentOptions();
//		assertEquals(null, user.currentOptions);
//	}
//
////	@Test
////	public void testChangeCurrentOptions() {
////		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
////		options.put(Field.COUNTRY, new ArrayList<String>());
////		options.get(Field.COUNTRY).add("США");
////		User user = new User("Даша", "Даша", null, null);
////		user.changeCurrentOptions(options);
////		assertTrue(user.currentOptions.keySet().contains(Field.COUNTRY));
////		assertEquals("США", user.currentOptions.get(Field.COUNTRY).get(0));
////	}
//
//}