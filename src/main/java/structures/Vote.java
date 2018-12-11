package structures;

public class Vote 
{
	private String userId;
	private String filmName;
	private boolean isLike;
	
	public Vote(String userId, String filmName, boolean isLike)
	{
		this.userId = userId;
		this.filmName = filmName;
		this.isLike = isLike;
	}

	public String getUserId()
	{
		return userId;
	}
	
	public String getFilmName()
	{
		return filmName;
	}
	
	public boolean isLike()
	{
		return isLike;
	}
}
