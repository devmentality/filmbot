package structures;

import java.util.Date;

public class Vote 
{
	private String userId;
	private String filmName;
	private boolean isLike;
	private Date date;
	
	public Vote(String userId, String filmName, boolean isLike, Date date)
	{
		this.userId = userId;
		this.filmName = filmName;
		this.isLike = isLike;
		this.date = date;
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
	
	public Date getVoteDate() {
		return date;
	}
}
