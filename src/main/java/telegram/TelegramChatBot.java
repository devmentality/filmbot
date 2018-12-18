package telegram;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Map;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import com.omertron.themoviedbapi.MovieDbException;
import storage.APIHandler;
import storage.InMemoryUserDataHandler;
import storage.VotesDatabase;

public class TelegramChatBot {

	private static String PROXY_HOST;
	private static Integer PROXY_PORT;
	private static String PROXY_USER;
	private static String PROXY_PASSWORD;
	private static String BOT_TOKEN;
	private static String BOT_USERNAME;
	private APIHandler apiDatabase;
	private VotesDatabase votesDatabase;
	private InMemoryUserDataHandler userDataHandler;

	public TelegramChatBot(APIHandler apiDatabase, VotesDatabase votesDatabase, 
			InMemoryUserDataHandler userDataHandler) throws MovieDbException {
		Map<String, String> env = System.getenv();
		PROXY_HOST = env.get("PROXY_HOST");
		PROXY_PORT = Integer.parseInt(env.get("PROXY_PORT"));
		PROXY_USER = env.get("PROXY_USER");
		PROXY_PASSWORD = env.get("PROXY_PASSWORD");
		BOT_TOKEN = env.get("BOT_TOKEN");
		BOT_USERNAME = env.get("BOT_USERNAME");
		this.apiDatabase = apiDatabase;
		this.votesDatabase = votesDatabase;
		this.userDataHandler = userDataHandler;
	}

	public void startTelegramChatBot() {
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
			}
		});

		ApiContextInitializer.init();

		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

		botOptions.setProxyHost(PROXY_HOST);
		botOptions.setProxyPort(PROXY_PORT);
		botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

		TelegramBot bot = new TelegramBot(apiDatabase, votesDatabase, userDataHandler, BOT_USERNAME, BOT_TOKEN, botOptions);

		try {
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiException e) {
			// e.printStackTrace();
		}
	}

}
