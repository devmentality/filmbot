package bot;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import dialog.Dialog;
import dialog.Phrases;
import storage.APIHandler;
import storage.InMemoryUserDataHandler;
import storage.VotesDatabase;

import structures.User;
import utils.UserUtils;

public class ChatBot {
	private APIHandler apiDatabase;
	private VotesDatabase votesDatabase;
	private InMemoryUserDataHandler userDataHandler;
	
	public ChatBot(APIHandler apiDatabase, VotesDatabase votesDatabase, InMemoryUserDataHandler userDataHandler) 
	{
		this.apiDatabase = apiDatabase;
		this.votesDatabase = votesDatabase;
		this.userDataHandler = userDataHandler;
	}

	public void startChat(InputStream inputStream, OutputStream outputStream) throws Exception
	{
		Scanner scan = new Scanner(inputStream);
		PrintStream printStream = new PrintStream(outputStream);

		printStream.println(Phrases.HELLO);
		String name = scan.nextLine();

		User user = UserUtils.getUser(userDataHandler, name, name);
        
		Dialog dialog = new Dialog(user, apiDatabase, votesDatabase, userDataHandler);

		printStream.println(dialog.startDialog());
		try {
			while (true) {
				String req = scan.nextLine();
				if ("/exit".equals(req)) {
					UserUtils.saveUser(userDataHandler, user);
					break;
				}
				String answer = dialog.processInput(req);
				printStream.println(answer);
			}
		} finally {
			scan.close();
		}
	}
}
