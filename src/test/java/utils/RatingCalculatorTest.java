package utils;

import utils.RatingCalculator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import structures.Vote;
import static org.junit.Assert.assertEquals;


public class RatingCalculatorTest 
{
	@Test
	public void shouldReturnRightRating()
	{
		List<Vote> votes = new ArrayList<Vote>();
		votes.add(new Vote("1", "film1", true));
		votes.add(new Vote("2", "film1", true));
		votes.add(new Vote("3", "film1", false));
		votes.add(new Vote("1", "film2", true));
		
		assertEquals(RatingCalculator.CalculateRating(votes, "film1"), 1);
	} 
	
}
