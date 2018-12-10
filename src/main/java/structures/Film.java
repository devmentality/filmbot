package structures;

public class Film {
	public String title;
	public String ID;

	public Film(String ID, String title) {
		this.title = title;
		this.ID = ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Film other = (Film) obj;

		return ID == other.ID || title.equals(other.title);
	}

	@Override
	public int hashCode() {
		int result = title.hashCode();
		result ^= ID.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return String.format(title);
	}
}
