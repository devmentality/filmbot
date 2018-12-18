package utils;


import storage.InMemoryUserDataHandler;
import structures.User;

public class UserUtils {

	public static User getUser(InMemoryUserDataHandler dataHandler, String name, String userFileID) 
	{
		User user = dataHandler.getUser(name, userFileID);
		if (user == null)
			user = new User(name, userFileID, null, null);
		return user;
	}

	public static void saveUser(InMemoryUserDataHandler dataHandler, User user) 
	{
		dataHandler.saveUser(user);
	}
}
