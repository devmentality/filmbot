package storage;

import org.junit.Before;
import org.junit.Test;
import storage.fakes.FilmDatabaseFileHandlerFake;
import structures.Vote;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;


public class VotesDatabaseTest 
{
	private VotesDatabase database;
	private IFilmDatabaseFileHandler fileHandler;
	
	@Before
	public void arrange()
	{
		fileHandler = new FilmDatabaseFileHandlerFake();
		database = new VotesDatabase(fileHandler);
	}

	@Test
	public void containsVote_shouldReturnFalse_whenNoVotes() throws IOException
	{
		assertFalse(database.containsVote("1", "film"));
	}
	
	@Test
	public void containsVote_shouldReturnFalse_whenHasAnotherFilmVote() throws IOException
	{
		database.addVote(new Vote("1", "film", true));
		assertFalse(database.containsVote("1", "another film"));
	}
	
	@Test
	public void containsVote_shouldReturnTrue_whenContainsSameVote() throws IOException
	{
		fileHandler.addData(new String[] {"1", "film", "true"});
		
		assertTrue(database.containsVote("1", "film"));
	}
	
	@Test
	public void addVote_shouldAddVote() throws IOException
	{
		database.addVote(new Vote("1", "film", true));
		
		List<String[]> records = fileHandler.extractData();
		
		assertEquals(records.size(), 1);
		assertArrayEquals(records.get(0), new String[] {"1", "film", "true"});
	}
	
	@Test
	public void getVotes() throws IOException
	{
		fileHandler.addData(new String[]{"1", "film", "true"});
		fileHandler.addData(new String[]{"2", "film", "true"});
		fileHandler.addData(new String[]{"2", "another film", "true"});
		List<String[]> records = fileHandler.extractData();
		
		List<Vote> votes = database.getVotes("film");
		assertEquals(votes.size(), 2);
		int index = 0;
		for(Vote vote: votes)
		{
			String[] record = new String[] {
					vote.getUserId(), vote.getFilmName(), String.valueOf(vote.isLike())};
			assertArrayEquals(records.get(index), record);
			index++;
		}
	}
}