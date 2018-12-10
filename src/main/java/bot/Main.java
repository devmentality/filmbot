package bot;

//import telegram.TelegramChatBot;

public class Main {

	public static void main(String[] args) throws Exception {
		startConsoleBot();
	}

//	public static void startTelegramBot() throws Exception {
//		TelegramChatBot bot = new TelegramChatBot(database);
//		bot.startTelegramChatBot();
//
//	}

	public static void startConsoleBot() throws Exception {
		ChatBot chatBot = new ChatBot(System.getenv("API_KEY"));
		chatBot.startChat(System.in, System.out);
	}
}
