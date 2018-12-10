//package storage;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//
//import structures.Field;
//import structures.User;
//
//import static org.hamcrest.CoreMatchers.hasItem;
//import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
//
//import static org.hamcrest.CoreMatchers.not;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertArrayEquals;
//
//public class UserDataHandlerTest {
//
//	private IFilmDatabaseFileHandler database;
//	private String userName;
//	private String userFileID;
//	private UserDataHandler userDataHandler;
//
//	private void initializeUserDataHandler(List<String[]> data) {
//		database = new TestFilmDatabaseFileHandler(data);
//		userName = "name";
//		userFileID = "fileID";
//		userDataHandler = new UserDataHandler(database, userName, userFileID);
//	}
//
////	private Map<Field, List<String>> getCurrentOptionsOne() {
////		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
////		options.put(Field.COUNTRY, new ArrayList<String>());
////		options.get(Field.COUNTRY).add("США");
////		return options;
////	}
////
////	private Map<Field, List<String>> getCurrentOptionsTwo() {
////		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
////		options.put(Field.COUNTRY, new ArrayList<String>());
////		options.get(Field.COUNTRY).add("США");
////		options.put(Field.YEAR, new ArrayList<String>());
////		options.get(Field.YEAR).add("1999");
////		return options;
////	}
//
//	@Test
//	public void testCreateUserEmptyDatabase() throws Exception {
//		List<String[]> data = new ArrayList<String[]>();
//		initializeUserDataHandler(data);
//
//		User user = userDataHandler.getUser();
//		assertEquals(new ArrayList<String>(), user.savedFilmsIDs);
//		assertEquals(userName, user.name);
//		assertEquals(userFileID, user.ID);
//	}
//
//	@Test
//	public void testCreateUserNoCurrentOption() throws Exception {
//		List<String[]> data = new ArrayList<String[]>();
//		String[] idRow = { "6" };
//		data.add(idRow);
//		initializeUserDataHandler(data);
//
//		User user = userDataHandler.getUser();
//		assertEquals(1, user.savedFilmsIDs.size());
//		assertEquals("6", user.savedFilmsIDs.get(0));
//		assertEquals(null, user.currentOptions);
//	}
//
////	@Test
////	public void testCreateUserWithCurrentOption() throws Exception {
////		List<String[]> data = new ArrayList<String[]>();
////		String[] idRow = { "6" };
////		String[] optionRow = { "COUNTRY", "США" };
////		data.add(idRow);
////		data.add(optionRow);
////		initializeUserDataHandler(data);
////
////		User user = userDataHandler.getUser();
////		assertEquals(1, user.savedFilmsIDs.size());
////		assertEquals("6", user.savedFilmsIDs.get(0));
////		assertThat(user.currentOptions.keySet(), hasItem(Field.COUNTRY));
////		assertThat(user.currentOptions.keySet(), not(hasItem(Field.YEAR)));
////		assertThat(user.currentOptions.get(Field.COUNTRY), hasItem("США"));
////	}
////
////	@Test
////	public void testSaveUserWithOneCurrentOption() throws Exception {
////		List<String> IDList = new ArrayList<String>();
////		IDList.add("8");
////		User user = new User("Вика", "Вика", IDList, getCurrentOptionsOne());
////		List<String[]> data = new ArrayList<String[]>();
////		initializeUserDataHandler(data);
////
////		String[] expectedIDRow = { "8" };
////		String[] expectedOptionRow = { "COUNTRY", "США" };
////
////		userDataHandler.saveUser(user);
////		TestFilmDatabaseFileHandler testDatabase = (TestFilmDatabaseFileHandler) database;
////		List<String[]> savedData = testDatabase.savedData;
////
////		assertEquals(2, savedData.size());
////		assertArrayEquals(expectedIDRow, savedData.get(0));
////		assertArrayEquals(expectedOptionRow, savedData.get(1));
////	}
//
////	@Test
////	public void testSaveUserWithTwoCurrentOption() throws Exception {
////		List<String> IDList = new ArrayList<String>();
////		IDList.add("8");
////		User user = new User("Вика", "Вика", IDList, getCurrentOptionsTwo());
////		List<String[]> data = new ArrayList<String[]>();
////		initializeUserDataHandler(data);
////
////		String[] expectedIDRow = { "8" };
////		String[] expectedOptionRow = { "COUNTRY", "США", "YEAR", "1999" };
////
////		userDataHandler.saveUser(user);
////		TestFilmDatabaseFileHandler testDatabase = (TestFilmDatabaseFileHandler) database;
////		List<String[]> savedData = testDatabase.savedData;
////
////		assertEquals(2, savedData.size());
////		assertArrayEquals(expectedIDRow, savedData.get(0));
////		assertThat(savedData.get(1), arrayContainingInAnyOrder(expectedOptionRow));
////	}
//
//	@Test
//	public void testSaveUserNoCurrentOption() throws Exception {
//		List<String> IDList = new ArrayList<String>();
//		IDList.add("8");
//		User user = new User("Вика", "Вика", IDList, null);
//		List<String[]> data = new ArrayList<String[]>();
//		initializeUserDataHandler(data);
//
//		String[] expectedIDRow = { "8" };
//
//		userDataHandler.saveUser(user);
//		TestFilmDatabaseFileHandler testDatabase = (TestFilmDatabaseFileHandler) database;
//		List<String[]> savedData = testDatabase.savedData;
//
//		assertEquals(1, savedData.size());
//		assertArrayEquals(expectedIDRow, savedData.get(0));
//	}
//
//}
