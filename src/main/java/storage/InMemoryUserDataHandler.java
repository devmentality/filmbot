package storage;

import java.util.HashMap;

import structures.User;
import structures.NameIdPair;

public class InMemoryUserDataHandler 
{	
	private HashMap<NameIdPair, User> users;
	
	public InMemoryUserDataHandler() 
	{
		users = new HashMap<NameIdPair, User>();
	}

	public synchronized User getUser(String userName, String id) 
	{
		User user = users.get(new NameIdPair(userName, id));
		if (user == null)
			return null;
		return (User)user.clone();
	}

	public synchronized void saveUser(User user)
	{
		users.put(new NameIdPair(user.name, user.id), (User)user.clone());
	}
}
