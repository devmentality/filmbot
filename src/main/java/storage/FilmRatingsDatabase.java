package storage;

import java.io.IOException;
import java.util.List;

public class FilmRatingsDatabase 
{
	private IFilmDatabaseFileHandler fileHandler;
	
	public FilmRatingsDatabase(IFilmDatabaseFileHandler fileHandler)
	{
		this.fileHandler = fileHandler;
	}
	
	public synchronized boolean Contains(String filmName) throws IOException
	{
		List<String[]> allRecords = fileHandler.extractData();
		for(String[] record: allRecords)
		{
			if (record[0].equals(filmName))
				return true;
		}
		
		return false;
	}
	
	public synchronized int GetRating(String filmName) throws IOException
	{
		List<String[]> allRecords = fileHandler.extractData();
		for(String[] record: allRecords)
		{
			if (record[0].equals(filmName))
				return Integer.parseInt(record[1]);
				
		}
		
		throw new IllegalArgumentException("No such film");
	}
	
	public synchronized void AddFilm(String filmName) throws IOException
	{
		fileHandler.addData(new String[] { filmName, "0" });
	}
	
	public synchronized void UpdateRating(String filmName, int ratingDelta) throws IOException
	{
		List<String[]> allRecords = fileHandler.extractData();
		for(String[] record: allRecords)
		{
			if (record[0].equals(filmName))
				record[1] += ratingDelta;
		}
		fileHandler.saveData(allRecords);
	}
}
