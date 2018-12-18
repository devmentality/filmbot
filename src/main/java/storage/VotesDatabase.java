package storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import structures.Vote;

public class VotesDatabase 
{
	private ArrayList<Vote> votes;
	
	public VotesDatabase() {
		votes = new ArrayList<>();
	}

	public synchronized boolean containsVote(String userId, String filmName) 
	{
		for (Vote vote: votes)
			if (vote.getUserId().equals(userId) && vote.getFilmName().equals(filmName))
				return true;

		return false;
	}

	public synchronized List<Vote> getVotes(String filmName) 
	{
		ArrayList<Vote> votesForFilm = new ArrayList<>();
		for(Vote vote: votes)
			if (vote.getFilmName().equals(filmName))
				votesForFilm.add(vote);
		return votesForFilm;
	}

	public synchronized List<Vote> getAllVotes() 
	{
		return new ArrayList<>(votes);
	}

	public synchronized void addVote(Vote vote)
	{
		votes.add(new Vote(vote.getUserId(), vote.getFilmName(), vote.isLike(), new Date()));
	}
}
