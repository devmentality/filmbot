package bot;

import storage.CSVHandler;
import storage.IFilmDatabaseFileHandler;
import storage.VotesDatabase;
import telegram.TelegramChatBot;
import storage.APIHandler;

public class Main {

	private static APIHandler apiDatabase;
	private static VotesDatabase votesDatabase;

	public static void main(String[] args) throws Exception {
        
		IFilmDatabaseFileHandler ratingsHandler = new CSVHandler("VotesDb");
		votesDatabase = new VotesDatabase(ratingsHandler);
		apiDatabase = new APIHandler(System.getenv("API_KEY"), votesDatabase);
		startConsoleBot();
	}
    

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(apiDatabase, votesDatabase);
		bot.startTelegramChatBot();
	}

	public static void startConsoleBot() throws Exception {
		ChatBot chatBot = new ChatBot(apiDatabase, votesDatabase);
		chatBot.startChat(System.in, System.out);
	}
}
