package structures;

import org.junit.Test;

import structures.Film;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FilmTest {

	@Test
	public void testEquals() {
		Film film1 = new Film("1", "Leon");
		Film film2 = new Film("2", "Leon");
		Film film3 = new Film("2", "leon");
		assertEquals(film1, film2);
		assertEquals(film1, film1);
		assertFalse(film1.equals(film3));
		assertEquals(film2, film3);
		assertFalse(film1.equals(null));
	}

	@Test
	public void testHashCode() {
		Film film1 = new Film("1", "Leon");
		Film film2 = new Film("1", "Leon");
		assertEquals(film1.hashCode(), film2.hashCode());
	}

	@Test
	public void testTostring() {
		Film film = new Film("1", "Leon");
		assertEquals("Leon", film.toString());
	}

}
