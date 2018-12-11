package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import structures.Vote;

public class VotesDatabase 
{
	private IFilmDatabaseFileHandler fileHandler;
	
	public VotesDatabase(IFilmDatabaseFileHandler fileHandler)
	{
		this.fileHandler = fileHandler;
	}
	
	public synchronized boolean containsVote(String userId, String filmName) throws IOException
	{
		List<String[]> allRecords = fileHandler.extractData();
		for(String[] record: allRecords)
			if (record[0].equals(userId) && record[1].equals(filmName))
				return true;
		
		return false;
	}
	
	public synchronized List<Vote> getVotes(String filmName) throws IOException
	{
		List<Vote> votes = new ArrayList<>();
		List<String[]> allRecords = fileHandler.extractData();
		
		for(String[] record: allRecords)
			if (record[1].equals(filmName))
				votes.add(new Vote(
						record[0], record[1], Boolean.parseBoolean(record[2])));
		
		return votes;
	}
	
	public synchronized void addVote(Vote vote) throws IOException
	{
		fileHandler.addData(new String[] { 
				String.valueOf(vote.getUserId()),
			    vote.getFilmName(),
			    String.valueOf(vote.isLike())
			   	});
	}
}
