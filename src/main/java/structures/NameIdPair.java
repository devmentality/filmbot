package structures;

public class NameIdPair
{
	private String name;
	private String id;
	
	public NameIdPair(String name, String id)
	{
		this.name = name;
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getId()
	{
		return id;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (!(other instanceof NameIdPair))
			return false;
		NameIdPair otherPair = (NameIdPair)other;
		return name.equals(otherPair.name) && id.equals(otherPair.id);
	}
	
	@Override
	public int hashCode()
	{
		return name.hashCode() * 19 ^ id.hashCode();
	}
}
