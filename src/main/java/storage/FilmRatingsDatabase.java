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
	
	public synchronized boolean contains(String filmName) throws IOException
	{
		List<String[]> allRecords = fileHandler.extractData();
		for(String[] record: allRecords)
			if (record[0].equals(filmName))
				return true;
		
		return false;
	}
	
	public synchronized int getRating(String filmName) throws IOException
	{
		List<String[]> allRecords = fileHandler.extractData();
		for(String[] record: allRecords)
			if (record[0].equals(filmName))
				return Integer.parseInt(record[1]);
		
		return 0;
	}
	
	public synchronized void addFilm(String filmName) throws IOException
	{
		fileHandler.addData(new String[] { filmName, "0" });
	}
	
	public synchronized void updateRating(String filmName, int ratingDelta) throws IOException
	{
		List<String[]> allRecords = fileHandler.extractData();
		for(String[] record: allRecords)
			if (record[0].equals(filmName))
				record[1] = String.valueOf(Integer.parseInt(record[1]) + ratingDelta);
		
		fileHandler.saveData(allRecords);
	}
}
