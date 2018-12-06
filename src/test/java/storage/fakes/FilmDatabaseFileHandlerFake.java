package storage.fakes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import storage.IFilmDatabaseFileHandler;

public class FilmDatabaseFileHandlerFake implements IFilmDatabaseFileHandler
{
	private List<String[]> rows;
	
	public FilmDatabaseFileHandlerFake()
	{
		rows = new ArrayList<String[]>();
	}
	
	@Override
	public void saveData(List<String[]> rows) throws IOException {
		this.rows = rows;
	}

	@Override
	public List<String[]> extractData() throws IOException {
		return rows;
	}

	@Override
	public void addData(String[] record) throws IOException {
		rows.add(record);
	}
}
