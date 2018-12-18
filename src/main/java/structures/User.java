package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Cloneable
{
	public String id;
	public String name;
	public List<String> savedFilmsIDs;
	public boolean firstTime;
	public Map<Field, List<String>> currentOptions;

	public User(String name, String id, List<String> savedFilmsIDs, Map<Field, List<String>> currentData) {
		this.name = name;
		this.id = id;
		firstTime = savedFilmsIDs == null;
		this.savedFilmsIDs = firstTime ? new ArrayList<String>() : savedFilmsIDs;

		currentOptions = currentData;
	}

	public void addFilm(Film film) {
		savedFilmsIDs.add(film.ID);
	}

	public void changeCurrentOptions(Map<Field, List<String>> data) {
		currentOptions = data;
	}

	public void clearCurrentOptions() {
		currentOptions = null;
	}

	@Override
	public Object clone()
	{
		try
		{
			User clonedUser = (User)super.clone();
			if (savedFilmsIDs != null)
				clonedUser.savedFilmsIDs = new ArrayList<>(savedFilmsIDs);
			
			clonedUser.currentOptions = new HashMap<Field, List<String>>();
			if (currentOptions != null)
				for(Field field: currentOptions.keySet())
					clonedUser.currentOptions.put(field, 
							new ArrayList<>(currentOptions.get(field)));
			return clonedUser;
			
		}
		catch(CloneNotSupportedException ex)
		{
			return null;
		}
	}
}