package storage;

import org.junit.Before;
import org.junit.Test;
import storage.fakes.FilmDatabaseFileHandlerFake;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class FilmRatingDatabaseTest 
{
	private FilmRatingsDatabase database;
	private String filmName = "Some film";
	
	@Before
	public void arrange()
	{
		database = new FilmRatingsDatabase(new FilmDatabaseFileHandlerFake());
	}

	@Test
	public void сontains_ShouldReturnFalse_WhenNoSuchFilm() throws IOException
	{
		assertFalse(database.contains(filmName));
	}
	
	@Test
	public void сontains_ShouldReturnTrue_WhenFilmAdded() throws IOException
	{
		database.addFilm(filmName);
		assertTrue(database.contains(filmName));
	}
	
	@Test
	public void getRating_ShouldReturnZero_AfterAdd() throws IOException
	{
		database.addFilm(filmName);
		assertEquals(0, database.getRating(filmName));
	}
	
	@Test
	public void updateRating_ShouldCorrectyChangeRating() throws IOException
	{
		int delta = 10;
		database.addFilm(filmName);
		database.updateRating(filmName, delta);
		
		assertEquals(delta, database.getRating(filmName));
	}
}
