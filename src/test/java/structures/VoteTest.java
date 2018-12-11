package structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VoteTest {
	private Vote vote = new Vote("12", "Matrix", true);
	
	@Test
	public void testGetUserId() {
		assertEquals("12", vote.getUserId());
	}
	
	@Test
	public void testGetFilmName() {
		assertEquals("Matrix", vote.getFilmName());
	}
	
	@Test
	public void isLike() {
		assertTrue(vote.isLike());
	}
}
