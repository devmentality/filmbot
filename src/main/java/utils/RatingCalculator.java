package utils;

import structures.Vote;
import java.util.List;

public class RatingCalculator 
{
	public static int CalculateRating(List<Vote> votes, String filmName)
	{
		int rating = 0;
		for(Vote vote: votes)
		{
			if (!filmName.equals(vote.getFilmName()))
				continue;
			if (vote.isLike())
				rating++;
			else
				rating--;
		}
		return rating;
	}
}
