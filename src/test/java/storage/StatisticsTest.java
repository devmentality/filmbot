package storage;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import storage.fakes.FilmDatabaseFileHandlerFake;
import structures.Vote;

public class StatisticsTest {
	private VotesDatabase votesDatabase;
	private IFilmDatabaseFileHandler fileHandler;
	private Statistics statistics;

	@Before
	public void setUp() throws Exception {
		fileHandler = new FilmDatabaseFileHandlerFake();
		votesDatabase = new VotesDatabase(fileHandler);
		createDatabase();
		statistics = new Statistics(votesDatabase);
	}

	private void createDatabase() throws Exception {
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        Date threeDays = calendar.getTime();
        votesDatabase.addVote(new Vote("user3", "lol", false, threeDays));
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -4);
        Date week = calendar.getTime();
        votesDatabase.addVote(new Vote("user1", "kek", false, week));
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        Date week2 = calendar.getTime();
        votesDatabase.addVote(new Vote("user3", "lol", true, week2));
		votesDatabase.addVote(new Vote("user1", "film", true, new Date()));
		votesDatabase.addVote(new Vote("user2", "kek", false, new Date()));
		votesDatabase.addVote(new Vote("user3", "film", true, new Date()));
	}

	@Test
	public void testGetMostActiveLikeUsers() {
		List<String> mostActiveLikeUsers = new ArrayList<String>();
		mostActiveLikeUsers.add("user3: 2");
		mostActiveLikeUsers.add("user1: 1");
		assertEquals(mostActiveLikeUsers, statistics.getMostActiveLikeUsers());
	}

	@Test
	public void testGetMostActiveDislikeUsers() {
		List<String> mostActiveDislikeUsers = new ArrayList<String>();
		mostActiveDislikeUsers.add("user3: 1");
		mostActiveDislikeUsers.add("user2: 1");
		mostActiveDislikeUsers.add("user1: 1");
		assertEquals(mostActiveDislikeUsers, statistics.getMostActiveDislikeUsers());
	}

	@Test
	public void testGetMostRatedFilms() throws IOException {
		List<String> mostRatedFilms = new ArrayList<String>();
		mostRatedFilms.add("film: 2");
		mostRatedFilms.add("lol: 0");
		mostRatedFilms.add("kek: -2");
		assertEquals(mostRatedFilms, statistics.getFilmRatingList());
	}

	@Test
	public void testGetTopUsersForPeriod() throws IOException {
		List<String> topUsers = new ArrayList<String>();
		topUsers.add("user3: 3");
		topUsers.add("user1: 2");
		topUsers.add("user2: 1");
		assertEquals(topUsers, statistics.getMarksStatisticForPeriod(7));
	}
}
