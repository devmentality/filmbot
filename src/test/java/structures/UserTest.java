package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import structures.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

	@Test
	public void testCreateUserFirstEntry() {
		User user = new User("a", "a", null, null);

		assertEquals("a", user.name);
		assertEquals(new ArrayList<String>(), user.savedFilmsIDs);
	}

	@Test
	public void testCreateUserSecondEntry() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("a", "a", IDList, null);

		assertEquals("a", user.name);
		assertEquals("8", user.savedFilmsIDs.get(0));
		assertEquals(1, user.savedFilmsIDs.size());
	}

	@Test
	public void testCreateUserEntryWithField() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
		User user = new User("a", "a", IDList, options);

		assertEquals("a", user.name);
		assertEquals("8", user.savedFilmsIDs.get(0));
		assertEquals(1, user.savedFilmsIDs.size());
		assertEquals(null, user.currentOptions.get(Field.YEAR));
	}

	@Test
	public void testAddFilm() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("a", "a", IDList, null);

		user.addFilm(new Film("13", "Pulp Fiction"));

		assertEquals("8", user.savedFilmsIDs.get(0));
		assertEquals("13", user.savedFilmsIDs.get(1));
		assertEquals(2, user.savedFilmsIDs.size());
	}

	@Test
	public void testClearCurrentField() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
		User user = new User("a", "a", IDList, options);
		user.clearCurrentOptions();
		assertEquals(null, user.currentOptions);
	}

	@Test
	public void testChangeCurrentOptions() {
		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
		options.put(Field.GENRE, new ArrayList<String>());
		options.get(Field.GENRE).add("Horror");
		User user = new User("a", "a", null, null);
		user.changeCurrentOptions(options);
		assertTrue(user.currentOptions.keySet().contains(Field.GENRE));
		assertEquals("Horror", user.currentOptions.get(Field.GENRE).get(0));
	}

}