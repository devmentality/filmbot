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
	public void Contains_ShouldReturnFalse_WhenNoSuchFilm() throws IOException
	{
		assertFalse(database.Contains(filmName));
	}
	
	@Test
	public void Contains_ShouldReturnTrue_WhenFilmAdded() throws IOException
	{
		database.AddFilm(filmName);
		assertTrue(database.Contains(filmName));
	}
	
	@Test
	public void GetRating_ShouldReturnZero_AfterAdd() throws IOException
	{
		database.AddFilm(filmName);
		assertEquals(0, database.GetRating(filmName));
	}
	
	@Test
	public void UpdateRating_ShouldCorrectyChangeRating() throws IOException
	{
		int delta = 10;
		database.AddFilm(filmName);
		database.UpdateRating(filmName, delta);
		
		assertEquals(delta, database.GetRating(filmName));
	}
}
