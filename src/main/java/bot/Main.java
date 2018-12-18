package bot;

import storage.VotesDatabase;
import telegram.TelegramChatBot;
import storage.APIHandler;
import storage.InMemoryUserDataHandler;

public class Main {

	private static APIHandler apiDatabase;
	private static VotesDatabase votesDatabase;
	private static InMemoryUserDataHandler userDataHandler;

	public static void main(String[] args) throws Exception 
	{
		votesDatabase = new VotesDatabase();
		apiDatabase = new APIHandler(System.getenv("API_KEY"), votesDatabase);
		userDataHandler = new InMemoryUserDataHandler();
		startTelegramBot();
	}
    

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(apiDatabase, votesDatabase, userDataHandler);
		bot.startTelegramChatBot();
	}

	public static void startConsoleBot() throws Exception {
		ChatBot chatBot = new ChatBot(apiDatabase, votesDatabase, userDataHandler);
		chatBot.startChat(System.in, System.out);
	}
}
