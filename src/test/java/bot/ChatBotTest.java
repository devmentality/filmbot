package bot;

import storage.APIHandler;
import storage.VotesDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.omertron.themoviedbapi.MovieDbException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

public class ChatBotTest {

	protected final ByteArrayOutputStream output = new ByteArrayOutputStream();
	protected final String name = "test_name";

	protected final String dialogStartFirst = "Назовите себя, пожалуйста\r\n" + "Добро пожаловать, test_name.";
	protected final String dialogStartSecond = "Назовите себя, пожалуйста\r\n" + "Давно не виделись, test_name.\r\n";

	private static APIHandler apiDatabase;
	private VotesDatabase votesDatabase = mock(VotesDatabase.class);

	private InputStream getInput(String[] commands) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		for (String command : commands) {
			builder.append(command);
			builder.append("\n");
		}
		return new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));
	}

	@Before
	public void setUp() throws MovieDbException {
		apiDatabase = new APIHandler(System.getenv("API_KEY"), votesDatabase);
	}
	
	private void tryToDeleteSavedFile() {
		File userFile = new File(name + ".csv");
		userFile.delete();
	}

	@Test
	public void testStartDialogFirstTime() throws Exception {
		String[] commands = { name, "/exit" };
		new ChatBot(apiDatabase, votesDatabase).startChat(getInput(commands), output);
		assertThat(output.toString(), containsString("Добро пожаловать"));
	}

	@Test
	public void testStartDialogSecondTime() throws Exception {
		String[] commands = { name, "/exit" };
		new ChatBot(apiDatabase, votesDatabase).startChat(getInput(commands), output);
		output.reset();
		new ChatBot(apiDatabase, votesDatabase).startChat(getInput(commands), output);
		assertThat(output.toString(), containsString("Давно не виделись"));
	}

	@Test
	public void testStartDialogGetFilm() throws Exception {
		String[] commands = new String[] { name, "/y 1999", "/exit" };
		new ChatBot(apiDatabase, votesDatabase).startChat(getInput(commands), output);
		assertThat(output.toString(), containsString("Fight Club"));
	}

	@Test
	public void testStartDialogGetFilmManyOptions() throws Exception {
		String[] commands = new String[] { name, "/y 2000 /g Comedy", "/exit" };
		new ChatBot(apiDatabase, votesDatabase).startChat(getInput(commands), output);
		assertThat(output.toString(), containsString("How the Grinch Stole Christmas"));
	}

	@Test
	public void testStartDialogGetFilmSecondTime() throws Exception {
		String[] commands = new String[]{ name, "/y 1999", "/exit" };
		new ChatBot(apiDatabase, votesDatabase).startChat(getInput(commands), output);
		output.reset();
		new ChatBot(apiDatabase, votesDatabase).startChat(getInput(commands), output);
		assertThat(output.toString(), containsString("The Matrix"));
	}

	@After
	public void tearDown() throws IOException {
		tryToDeleteSavedFile();
	}

}