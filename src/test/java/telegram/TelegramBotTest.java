package telegram;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TelegramBotTest {

	@Test
	public void TwoUsersTestDifferentRequest() throws Exception {
		TelegramBot bot = mock(TelegramBot.class);
		State state1 = bot.getState("/y 1972", "1", DialogState.BASIC);
		when(bot.getAnswer(state1, "name", "1")).thenReturn("The Godfather");
		State state2 = bot.getState("/c США", "2", DialogState.BASIC);
		when(bot.getAnswer(state2, "name2", "2")).thenReturn("Fight Club");

		assertEquals(bot.getAnswer(state1, "name", "1"), "The Godfather");
		assertEquals(bot.getAnswer(state2, "name2", "2"), "Fight Club");
	}

	@Test
	public void TwoUsersTestSameRequest() throws Exception {
		TelegramBot bot = mock(TelegramBot.class);
		State state1 = bot.getState("/y 1972", "1", DialogState.BASIC);
		when(bot.getAnswer(state1, "name", "1")).thenReturn("The Godfather");
		State state2 = bot.getState("/y 1972", "2", DialogState.BASIC);
		when(bot.getAnswer(state2, "name2", "2")).thenReturn("The Godfather");

		assertEquals(bot.getAnswer(state1, "name", "1"), "The Godfather");
		assertEquals(bot.getAnswer(state2, "name2", "2"), "The Godfather");
	}

	@After
	public void tearDown() {
		tryToDeleteSavedFile();
	}

	@Before
	public void setUp() {
		tryToDeleteSavedFile();
	}

	private void tryToDeleteSavedFile() {
		File userFile = new File("1" + ".csv");
		File userFile2 = new File("2" + ".csv");
		userFile.delete();
		userFile2.delete();
	}
}
