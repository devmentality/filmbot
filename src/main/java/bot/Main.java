package bot;

import storage.APIHandler;

import telegram.TelegramChatBot;

public class Main {
	
	private static APIHandler apiDatabase;

	public static void main(String[] args) throws Exception {
		apiDatabase = new APIHandler(System.getenv("API_KEY"));
		startConsoleBot();
//		startTelegramBot();
	}

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(apiDatabase);
		bot.startTelegramChatBot();

	}

	public static void startConsoleBot() throws Exception {
		ChatBot chatBot = new ChatBot(apiDatabase);
		chatBot.startChat(System.in, System.out);
	}
}
