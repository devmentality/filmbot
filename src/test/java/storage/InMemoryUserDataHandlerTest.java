package storage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;


import storage.InMemoryUserDataHandler;
import structures.User;

public class InMemoryUserDataHandlerTest 
{
	private InMemoryUserDataHandler userDataHandler;
	
	@Before
	public void arrange()
	{
		userDataHandler = new InMemoryUserDataHandler();
	}
	
	@Test
	public void getUser_shouldReturnNull_whenNoSuchUser()
	{
		assertNull(userDataHandler.getUser("name", "id"));
	}
	
	@Test
	public void getUser_shouldReturnEqualUser_whenHasSuchUser()
	{
		User user = new User("name", "id", null, null);
		userDataHandler.saveUser(user);
		
		User gotUser = userDataHandler.getUser("name", "id");
		
		assertEquals(user.name, gotUser.name);
		assertEquals(user.id, gotUser.id);
	}
}
