package structures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RatedFilmTest {
	private RatedFilm ratedFilm1 = new RatedFilm(new Film("1", "Leon"), 6);
	private RatedFilm ratedFilm2 = new RatedFilm(new Film("11", "Fight Club"), 6);
	private RatedFilm ratedFilm3 = new RatedFilm(new Film("111", "Pulp Fiction"), 13);
	
	@Test
	public void testGetFilm() {
		assertEquals(new Film("1", "Leon"), ratedFilm1.getFilm());
		assertEquals(new Film("111", "Pulp Fiction"), ratedFilm3.getFilm());
	}
	
	@Test
	public void testGetRating() {
		assertEquals(6, ratedFilm1.getRating());
		assertEquals(13, ratedFilm3.getRating());
	}
	
	@Test
	public void testCompareTo() {
		assertEquals(ratedFilm1.compareTo(ratedFilm2), 0);
		assertEquals(ratedFilm2.compareTo(ratedFilm3), -1);
		assertEquals(ratedFilm3.compareTo(ratedFilm1), 1);
	}
}
