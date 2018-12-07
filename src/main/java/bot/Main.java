package bot;

import storage.CSVHandler;
import storage.FileFilmHandler;
import storage.IFilmHandler;
import storage.IFilmDatabaseFileHandler;
import storage.FilmDatabase;
import storage.FilmRatingsDatabase;
import telegram.TelegramChatBot;

public class Main {

	private static FilmDatabase database;
	private static FilmRatingsDatabase ratingsDatabase;

	public static void main(String[] args) throws Exception {

		IFilmDatabaseFileHandler fileHandler = new CSVHandler("Database");
		IFilmDatabaseFileHandler ratingsHandler = new CSVHandler("RatingsDb");
		
		IFilmHandler filmHandler = new FileFilmHandler(fileHandler);
		database = new FilmDatabase(filmHandler);
		ratingsDatabase = new FilmRatingsDatabase(ratingsHandler);
		startConsoleBot();
	}

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(database, ratingsDatabase);
		bot.startTelegramChatBot();

	}

	public static void startConsoleBot() throws Exception {
		ChatBot chatBot = new ChatBot(database, ratingsDatabase);
		chatBot.startChat(System.in, System.out);
	}

}
