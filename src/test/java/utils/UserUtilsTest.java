package utils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage.InMemoryUserDataHandler;
import structures.Field;
import structures.User;
import utils.UserUtils;

public class UserUtilsTest {

	private List<String> idList;
	private User user;
	private Map<Field, List<String>> options;
	private InMemoryUserDataHandler userDataHandler;

	private void createFilmList() {
		idList = new ArrayList<String>();
		idList.add("12");
		idList.add("13");
	}

	@Before
	public void setUp()  
	{
		userDataHandler = new InMemoryUserDataHandler();
				
		createFilmList();
		options = new HashMap<Field, List<String>>();
		options.put(Field.GENRE, new ArrayList<String>());
		options.get(Field.GENRE).add("Action");
		user = new User("a", "a", idList, options);
		UserUtils.saveUser(userDataHandler, user);
	}

	@Test
	public void testGetUser() {
		assertEquals(UserUtils.getUser(userDataHandler, user.name, user.id).savedFilmsIDs, user.savedFilmsIDs);
		assertEquals(UserUtils.getUser(userDataHandler, user.name, user.id).currentOptions, options);
	}
}
