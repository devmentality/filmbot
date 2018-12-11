package dialog;

import storage.APIHandler;

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
	private APIHandler apiDatabase = mock(APIHandler.class);

	@Before
	public void setUp() throws Exception {
		List<String> userList = new ArrayList<String>();
		user = new User("name", "name", userList, null);
		dialog = new Dialog(user, apiDatabase, null);
	}

	@Test
	public void testStartDialogFirstTime() throws Exception {
		user = new User("name", "name", null, null);
		dialog = new Dialog(user, apiDatabase, null);
		assertEquals(String.format("Добро пожаловать, name.%s", Phrases.HELP), dialog.startDialog());
	}

	@Test
	public void testStartDialogNotFirstTime() {
		assertEquals("Давно не виделись, name.", dialog.startDialog());
	}

	@Test
	public void testProcessInputHelp() throws MovieDbException {
		assertEquals(Phrases.HELP, dialog.processInput("/help"));
	}

	@Test
	public void testProcessInputShort() throws MovieDbException {
		assertEquals(Phrases.SHORT_COMMAND, dialog.processInput("c:"));
	}

	@Test
	public void testGetYear() throws MovieDbException {
		dialog = mock(Dialog.class);
		when(dialog.processInput("/y 1994")).thenReturn("Leon");
		assertEquals("Leon", dialog.processInput("/y 1994"));
	}

	@Test
	public void testGetNextYear() throws MovieDbException {
		dialog = mock(Dialog.class);
		when(dialog.processInput("/y 1994")).thenReturn("Leon");
		when(dialog.processInput("/next")).thenReturn("Pulp Fiction");
		assertEquals("Leon", dialog.processInput("/y 1994"));
		assertEquals("Pulp Fiction", dialog.processInput("/next"));
	}

	@Test
	public void testGetGenre() throws MovieDbException {
		dialog = mock(Dialog.class);
		when(dialog.processInput("/g Thriller")).thenReturn("Fight Club");
		assertEquals("Fight Club", dialog.processInput("/g Thriller"));
	}

	@Test
	public void testGetNextGenre() throws MovieDbException {
		dialog = mock(Dialog.class);
		when(dialog.processInput("/g Thriller")).thenReturn("Fight Club").thenReturn("Leon");
		assertEquals("Fight Club", dialog.processInput("/g Thriller"));
		assertEquals("Leon", dialog.processInput("/g Thriller"));
	}

	@Test
	public void testUnknownCommand() throws MovieDbException {
		assertEquals(Phrases.UNKNOWN_COMMAND, dialog.processInput("/p lol"));
	}

	@Test
	public void testGetYearWhichNotInFilmList() throws MovieDbException {
		assertEquals(Phrases.NO_SUCH_FILM, dialog.processInput("/y 900"));
	}

	@Test
	public void testGetGenreWhichNotInFilmList() throws MovieDbException {
		assertEquals(Phrases.NO_SUCH_FILM, dialog.processInput("/g телепузик"));
	}

	@Test
	public void testNextWithoutOption() throws MovieDbException {
		assertEquals(Phrases.NEXT_WITHOUT_OPT, dialog.processInput("/next"));
	}

	@Test
	public void testYearAndGenre() throws MovieDbException {
		dialog = mock(Dialog.class);
		when(dialog.processInput("/y 1994 /g Comedy")).thenReturn("Pulp Fiction");
		assertEquals("Pulp Fiction", dialog.processInput("/y 1994 /g Comedy"));
	}

	@Test
	public void testTwoGenres() throws MovieDbException {
		dialog = mock(Dialog.class);
		when(dialog.processInput("/g Crime /g Comedy")).thenReturn("Pulp Fiction");
		assertEquals("Pulp Fiction", dialog.processInput("/g Crime /g Comedy"));
	}

	@Test
	public void testTwoYears() throws MovieDbException {
		assertEquals(Phrases.NO_SUCH_FILM, dialog.processInput("/y 1999 /y 1994"));
	}

}
