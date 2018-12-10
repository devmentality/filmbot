//package bot;
//
//import storage.CSVHandler;
//import storage.FileFilmHandler;
//import storage.FilmDatabase;
//import storage.IFilmHandler;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import dialog.Phrases;
//
//import static org.junit.Assert.assertThat;
//import static org.hamcrest.CoreMatchers.containsString;
//
//public class ChatBotTest {
//
//	protected final ByteArrayOutputStream output = new ByteArrayOutputStream();
//	protected final String name = "test_name";
//
//	protected final String dialogStartFirst = "Назовите себя, пожалуйста\r\n" + "Добро пожаловать, test_name.";
//	protected final String dialogStartSecond = "Назовите себя, пожалуйста\r\n" + "Давно не виделись, test_name.\r\n";
//
//	private static CSVHandler csvHandler;
//
//	private InputStream getInput(String[] commands) throws UnsupportedEncodingException {
//		StringBuilder builder = new StringBuilder();
//		for (String command : commands) {
//			builder.append(command);
//			builder.append("\n");
//		}
//		return new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));
//	}
//
//	private void tryToDeleteSavedFile() {
//		File userFile = new File(name + ".csv");
//		userFile.delete();
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		csvHandler = new CSVHandler("testDatabase");
//		filmHandler = new FileFilmHandler(csvHandler);
//		database = new FilmDatabase(filmHandler);
//	}
//
//	@Test
//	public void testStartDialogFirstTime() throws Exception {
//		String[] commands = { name, "/exit" };
//		new ChatBot(database).startChat(getInput(commands), output);
//		assertThat(output.toString(), containsString("Добро пожаловать"));
//	}
//
//	@Test
//	public void testStartDialogSecondTime() throws Exception {
//		String[] commands = { name, "/exit" };
//		new ChatBot(database).startChat(getInput(commands), output);
//		output.reset();
//		new ChatBot(database).startChat(getInput(commands), output);
//		assertThat(output.toString(), containsString("Давно не виделись"));
//	}
//
//	@Test
//	public void testStartDialogGetFilm() throws Exception {
//		String[] commands = { name, "/y 1999", "/exit" };
//		new ChatBot(database).startChat(getInput(commands), output);
//		assertThat(output.toString(), containsString("Бойцовский клуб"));
//	}
//
//	@Test
//	public void testStartDialogGetFilmManyOptions() throws Exception {
//		String[] commands = { name, "/c США /g комедия", "/exit" };
//		new ChatBot(database).startChat(getInput(commands), output);
//		assertThat(output.toString(), containsString("Криминальное чтиво"));
//	}
//
//	@Test
//	public void testStartDialogGetFilmSecondTime() throws Exception {
//		String[] commands = { name, "/y 1999", "/exit" };
//		new ChatBot(database).startChat(getInput(commands), output);
//		output.reset();
//		new ChatBot(database).startChat(getInput(commands), output);
//		assertThat(output.toString(), containsString(Phrases.NO_MORE_FILM));
//	}
//
//	@After
//	public void tearDown() throws IOException {
//		tryToDeleteSavedFile();
//	}
//
//}
