package dialog;

public class Phrases {
	public static final String HELLO = "Назовите себя, пожалуйста";

	public static final String HELP = "\nЭтот бот кидает кино по вашим запросам.\n" + "Формат ввода:\n" + "/y год\n"
			+ "/g жанр\n" + "\n" + "/next следующий фильм\n"
			+ "/help справка\n" + "/add добавление фильма в базу\n" + "/adding справка по добавлению фильма в базу\n"
			+ "/genres доступные жанры\n" + "И помните, если фильм был уже кинут по какому-либо из параметров, "
			+ "он не может быть кинут как по тому же самому или другому параметру ещё раз.";

	public static final String UNKNOWN_COMMAND = "Неизвестная команда, загляни, пожалуйста, в справку";

	public static final String SHORT_COMMAND = "Слишком короткая команда, не могу понять :с";

	public static final String YEAR_NAN = "Ну как так, год должен быть числом";

	public static final String NEXT_WITHOUT_OPT = "Дружок, сначала выбери опцию, а потом проси фильм";

	public static final String DATABASE_ERROR = "Ошибочка с базой данных, перепроверьте её";

	public static final String SAVE_USER_ERROR = "Ошибочка при сохранении пользователя";

	public static final String NO_SUCH_FILM = "Нет фильмов с такими параметрами";

	public static final String NO_MORE_FILM = "Все фильмы с данными параметрами из базы были предоставлены";

	public static final String AVAILAIBLE_GENRES = "Доступные жанры фильмов:\n";

	public static final String AVAILAIBLE_YEARS = "Доступные года выпуска фильмов: 1900-2019";

}
