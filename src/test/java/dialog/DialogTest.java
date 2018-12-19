package dialog;

import storage.APIHandler;
import storage.InMemoryUserDataHandler;
import storage.VotesDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.omertron.themoviedbapi.MovieDbException;

import dialog.Dialog;
import dialog.Phrases;
import structures.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DialogTest {
	private User user;
	private Dialog dialog;
	private APIHandler apiDatabase;
	private VotesDatabase votesDatabase = mock(VotesDatabase.class);

	@Before
	public void setUp() throws Exception {
		List<String> userList = new ArrayList<String>();
		apiDatabase = new APIHandler(System.getenv("API_KEY"), votesDatabase);
		user = new User("name", "name", userList, null);
		dialog = new Dialog(user, apiDatabase, votesDatabase, new InMemoryUserDataHandler());
	}

	@Test
	public void testStartDialogFirstTime() throws Exception {
		user = new User("name", "name", null, null);
		dialog = new Dialog(user, apiDatabase, votesDatabase, new InMemoryUserDataHandler());
		assertEquals(String.format("Добро пожаловать, name.%s", Phrases.HELP), dialog.startDialog());
	}

	@Test
	public void testStartDialogNotFirstTime() {
		assertEquals("Давно не виделись, name.", dialog.startDialog());
	}

	@Test
	public void testProcessInputHelp() throws MovieDbException, IOException {
		assertEquals(Phrases.HELP, dialog.processInput("/help"));
	}

	@Test
	public void testProcessInputShort() throws MovieDbException, IOException {
		assertEquals(Phrases.SHORT_COMMAND, dialog.processInput("c:"));
	}

	@Test
	public void testGetYear() throws MovieDbException, IOException {
		assertEquals("Pulp Fiction\nРейтинг: 0", dialog.processInput("/y 1994"));
	}

	@Test
	public void testGetNextYear() throws MovieDbException, IOException {
		assertEquals("Pulp Fiction\nРейтинг: 0", dialog.processInput("/y 1994"));
		assertEquals("The Shawshank Redemption\nРейтинг: 0", dialog.processInput("/next"));
	}

	@Test
	public void testGetGenre() throws MovieDbException, IOException {
		assertEquals("The Predator\nРейтинг: 0", dialog.processInput("/g Thriller"));
	}

	@Test
	public void testGetNextGenre() throws MovieDbException, IOException {
		assertEquals("The Predator\nРейтинг: 0", dialog.processInput("/g Thriller"));
		assertEquals("Hunter Killer\nРейтинг: 0", dialog.processInput("/next"));
	}

	@Test
	public void testUnknownCommand() throws MovieDbException, IOException {
		assertEquals(Phrases.UNKNOWN_COMMAND, dialog.processInput("/p lol"));
	}

	@Test
	public void testGetYearWhichNotInFilmList() throws MovieDbException, IOException {
		assertEquals(Phrases.NO_SUCH_FILM, dialog.processInput("/y 900"));
	}

	@Test
	public void testGetGenreWhichNotInFilmList() throws MovieDbException, IOException {
		assertEquals(Phrases.NO_SUCH_FILM, dialog.processInput("/g телепузик"));
	}

	@Test
	public void testNextWithoutOption() throws MovieDbException, IOException {
		assertEquals(Phrases.NEXT_WITHOUT_OPT, dialog.processInput("/next"));
	}

	@Test
	public void testYearAndGenre() throws MovieDbException, IOException {
		assertEquals("Forrest Gump\nРейтинг: 0", dialog.processInput("/y 1994 /g Comedy"));
	}

	@Test
	public void testTwoGenres() throws MovieDbException, IOException {
		assertEquals("A Simple Favor\nРейтинг: 0", dialog.processInput("/g Crime /g Comedy"));
	}

	@Test
	public void testTwoYears() throws MovieDbException, IOException {
		assertEquals(Phrases.NO_SUCH_FILM, dialog.processInput("/y 1999 /y 1994"));
	}

}
