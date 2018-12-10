//package utils;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import structures.Field;
//import structures.User;
//import utils.UserUtils;
//
//public class UserUtilsTest {
//
//	private List<String> idList;
//	private User user;
//	private Map<Field, List<String>> options;
//
//	private void createFilmList() {
//		idList = new ArrayList<String>();
//		idList.add("12");
//		idList.add("13");
//	}
//
////	@Before
////	public void createUser() throws Exception {
////		createFilmList();
////		options = new HashMap<Field, List<String>>();
////		options.put(Field.COUNTRY, new ArrayList<String>());
////		options.get(Field.COUNTRY).add("США");
////		user = new User("a", "a", idList, options);
////		UserUtils.saveUser(user);
////	}
//
//	@Test
//	public void testGetUser() {
//		assertEquals(UserUtils.getUser(user.name, user.ID).savedFilmsIDs, user.savedFilmsIDs);
//		assertEquals(UserUtils.getUser(user.name, user.ID).currentOptions, options);
//	}
//
//	@After
//	public void deleteFile() {
//		File file = new File(user.ID + ".csv");
//		file.delete();
//	}
//}
