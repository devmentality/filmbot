//package structures;
//
//import org.junit.Test;
//
//import structures.Film;
//import structures.Field;
//import utils.FilmUtils;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class FilmTest {
//
//	@Test
//	public void testGetField() {
//		List<String> countries = new ArrayList<String>(Arrays.asList(new String[] { "Франция" }));
//		List<String> year = new ArrayList<String>(Arrays.asList(new String[] { "1994" }));
//		List<String> genres = new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }));
//		Film film = FilmUtils.getFilm("5", "Леон", countries, year, genres);
//		assertEquals(year, film.getField(Field.YEAR));
//		assertEquals(genres, film.getField(Field.GENRE));
//	}
//
//	@Test
//	public void testEquals() {
//		List<String> countries = new ArrayList<String>(Arrays.asList(new String[] { "Франция" }));
//		List<String> year = new ArrayList<String>(Arrays.asList(new String[] { "1994" }));
//		List<String> genres = new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }));
//		List<String> year2 = new ArrayList<String>(Arrays.asList(new String[] { "1999" }));
//		List<String> genres2 = new ArrayList<String>(Arrays.asList(new String[] { "драма", "криминал", "триллер" }));
//		Film film1 = FilmUtils.getFilm("1", "Леон", countries, year, genres);
//		Film film2 = FilmUtils.getFilm("2", "Леон", countries, year, genres2);
//		Film film3 = FilmUtils.getFilm("2", "леон", countries, year2, genres);
//		assertEquals(film1, film2);
//		assertEquals(film1, film1);
//		assertFalse(film1.equals(film3));
//		assertEquals(film2, film3);
//		assertFalse(film1.equals(null));
//	}
//
//	@Test
//	public void testHashCode() {
//		List<String> countries = new ArrayList<String>(Arrays.asList(new String[] { "Франция" }));
//		List<String> year = new ArrayList<String>(Arrays.asList(new String[] { "1994" }));
//		List<String> genres = new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }));
//		Film film1 = FilmUtils.getFilm("1", "Леон", countries, year, genres);
//		Film film2 = FilmUtils.getFilm("1", "Леон", countries, year, genres);
//		assertEquals(film1.hashCode(), film2.hashCode());
//	}
//
//	@Test
//	public void testTostring() {
//		List<String> countries = new ArrayList<String>(Arrays.asList(new String[] { "США", "Великобритания" }));
//		List<String> year = new ArrayList<String>(Arrays.asList(new String[] { "2006" }));
//		List<String> genres = new ArrayList<String>(Arrays.asList(new String[] { "фантастика", "триллер", "драма" }));
//		Film film = FilmUtils.getFilm("14", "Престиж", countries, year, genres);
//		assertEquals("Название: Престиж, год: 2006, страна: США, Великобритания, жанр: фантастика, триллер, драма",
//				film.toString());
//	}
//
//}
