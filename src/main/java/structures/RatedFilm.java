package structures;

public class RatedFilm implements Comparable<RatedFilm>
{
	private Film film;
	private int rating; 
	
	public RatedFilm(Film film, int rating)
	{
		this.film = film;
		this.rating = rating;
	}
	
	public Film getFilm()
	{
		return film;
	}
	
	public int getRating()
	{
		return rating;
	}

	@Override
	public int compareTo(RatedFilm other) 
	{
		if (rating > other.getRating())
			return 1;
		if (rating < other.getRating())
			return -1;
		return 0;
	}
	
	
}
