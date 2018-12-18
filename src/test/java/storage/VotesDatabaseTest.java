package storage;

import org.junit.Before;
import org.junit.Test;
import storage.fakes.FilmDatabaseFileHandlerFake;
import structures.Vote;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;


public class VotesDatabaseTest 
{
	private VotesDatabase database;
	
	@Before
	public void arrange()
	{
		database = new VotesDatabase();
	}

	@Test
	public void containsVote_shouldReturnFalse_whenNoVotes() throws IOException
	{
		assertFalse(database.containsVote("1", "film"));
	}
	
	@Test
	public void containsVote_shouldReturnFalse_whenHasAnotherFilmVote() throws IOException
	{
		database.addVote(new Vote("1", "film", true, new Date()));
		assertFalse(database.containsVote("1", "another film"));
	}

	@Test
	public void containsVote_shouldReturnTrue_afterAddVote() throws IOException
	{
		database.addVote(new Vote("1", "film", true, new Date()));
		assertTrue(database.containsVote("1", "film"));
	}
	
	@Test
	public void getVotes() throws IOException
	{
		database.addVote(new Vote("1", "film", true, new Date()));
		database.addVote(new Vote("2", "another film", true, new Date()));
		database.addVote(new Vote("2", "film", true, new Date()));
		
		
		List<Vote> votes = database.getVotes("film");
		assertEquals(votes.size(), 2);
	}
}