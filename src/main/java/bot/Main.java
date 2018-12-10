package bot;

import storage.CSVHandler;
import storage.IFilmDatabaseFileHandler;
import storage.FilmRatingsDatabase;
import telegram.TelegramChatBot;
import storage.APIHandler;

public class Main {

	private static APIHandler apiDatabase;
	private static FilmRatingsDatabase ratingsDatabase;

	public static void main(String[] args) throws Exception {
        apiDatabase = new APIHandler(System.getenv("API_KEY"));
		IFilmDatabaseFileHandler ratingsHandler = new CSVHandler("RatingsDb");
		ratingsDatabase = new FilmRatingsDatabase(ratingsHandler);
		startConsoleBot();
	}
    

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(apiDatabase, ratingsDatabase);
		bot.startTelegramChatBot();
	}

	public static void startConsoleBot() throws Exception {
		ChatBot chatBot = new ChatBot(apiDatabase, ratingsDatabase);
		chatBot.startChat(System.in, System.out);
	}
}
