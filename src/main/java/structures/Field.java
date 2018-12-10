package structures;

public enum Field {
	YEAR("y", "Все фильмы этого года, имеющиеся в базе, были предоставлены", "В базе нет фильмов, снятых в этот год :с",
			"Теперь выберите год"),
	GENRE("g", "Все фильмы этого жанра, имеющиеся в базе, были предоставлены",
			"В базе нет фильмов, снятых в этом жанре :с", "Теперь выберите жанр");

	String shortCut;
	String noFilmsLeft;
	String noFilmsAtAll;
	String nowChoose;

	Field(String shortCut, String noFilmsLeft, String noFilmsAtAll, String nowChoose) {
		this.shortCut = shortCut;
		this.noFilmsLeft = noFilmsLeft;
		this.noFilmsAtAll = noFilmsAtAll;
		this.nowChoose = nowChoose;
	}

	public String noFilmsLeft() {
		return noFilmsLeft;
	}

	public String noFilmsAtAll() {
		return noFilmsAtAll;
	}

	public String shortCut() {
		return shortCut;
	}

	public String nowChoose() {
		return nowChoose;
	}

}