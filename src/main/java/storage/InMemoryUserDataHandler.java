package storage;

import java.util.HashMap;

import structures.User;
import structures.NameIdPair;

public class InMemoryUserDataHandler 
{	
	private HashMap<NameIdPair, User> users;
	private HashMap<String, String> idToName;
	
	public InMemoryUserDataHandler() 
	{
		users = new HashMap<NameIdPair, User>();
		idToName = new HashMap<String, String>();
	}
	
	public synchronized String getNameById(String id)
	{
		return idToName.get(id);
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
		idToName.put(user.id, user.name);
		users.put(new NameIdPair(user.name, user.id), (User)user.clone());
	}
}
