package storage;

import java.io.IOException;
import java.util.List;

public interface IFilmDatabaseFileHandler {

	public void saveData(List<String[]> rows) throws IOException;

	public List<String[]> extractData() throws IOException;

	public void addData(String[] record) throws IOException;

}