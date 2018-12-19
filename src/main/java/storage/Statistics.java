package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import structures.Vote;
import utils.RatingCalculator;

public class Statistics {

	private VotesDatabase votesDatabase;
	private List<Vote> votes;
	private InMemoryUserDataHandler userDataHandler;
	
	public Statistics(VotesDatabase votesDatabase, InMemoryUserDataHandler userDataHandler) throws IOException {
		this.votesDatabase = votesDatabase;
		votes = this.votesDatabase.getAllVotes();
		this.userDataHandler = userDataHandler;
	}

	public List<String> getMostActiveMarkUsers(Boolean isLikes) {
		Map<String, Integer> userLikes = new HashMap<String, Integer>();
		Map<String, Integer> userDislikes = new HashMap<String, Integer>();
		for (Vote vote : votes) {
			String userName = userDataHandler.getNameById(vote.getUserId());
			if (vote.isLike())
				if (!userLikes.containsKey(userName))
					userLikes.put(userName, 1);
				else
					userLikes.put(userName, userLikes.get(userName) + 1);
			if (!vote.isLike())
				if (!userDislikes.containsKey(userName))
					userDislikes.put(userName, 1);
				else
					userDislikes.put(userName, userDislikes.get(userName) + 1);
		}
		List<String> usersMarksData = new ArrayList<String>();
		Map<String, Integer> marks = isLikes ? userLikes : userDislikes;
		for (Entry<String, Integer> likesData : marks.entrySet()) {
			int marksCount = likesData.getValue();
			if (marksCount == 0)
				continue;
			usersMarksData.add(Integer.toString(marksCount) + ": " + likesData.getKey());
		}
		Collections.sort(usersMarksData);
		Collections.reverse(usersMarksData);
		List<String> marksList = new ArrayList<String>();
		int count = 0;
		for (String userMarksData : usersMarksData) {
			count++;
			String[] marksData = userMarksData.split(": ", 2);
			marksList.add(marksData[1] + ": " + marksData[0]);
			if (count == 10)
				break;
		}
		return marksList;
	}

	public List<String> getFilmRatingList() throws IOException {
		List<String> votedFilms = new ArrayList<String>();
		List<Vote> votes = votesDatabase.getAllVotes();
		for (int i = 0; i < votes.size(); i++) {
			String filmName = votes.get(i).getFilmName();
			if (!votedFilms.contains(filmName))
				votedFilms.add(votes.get(i).getFilmName());
		}
		List<String> filmsRatingData = new ArrayList<String>();
		for (String filmName : votedFilms) {
			int rating = RatingCalculator.CalculateRating(votesDatabase.getVotes(filmName), filmName);
			filmsRatingData.add(Integer.toString(rating) + ": " + filmName);
		}
		Collections.sort(filmsRatingData);
		Collections.reverse(filmsRatingData);
		List<String> filmsRatingList = new ArrayList<String>();
		int count = 0;
		for (String film : filmsRatingData) {
			count++;
			String[] filmData = film.split(": ", 2);
			filmsRatingList.add(filmData[1] + ": " + filmData[0]);
			if (count == 10)
				break;
		}
		return filmsRatingList;
	}

	public List<String> getMarksStatisticForPeriod(int period) throws IOException {
		Map<String, Integer> userActivity = new HashMap<String, Integer>();
		List<Vote> votes = votesDatabase.getAllVotes();
		Date dateNow = new Date();
		for (Vote vote : votes) {
			long dayDelta = (dateNow.getTime() - vote.getVoteDate().getTime()) / (24 * 60 * 60 * 1000);
			String userName = userDataHandler.getNameById(vote.getUserId());
			if (dayDelta <= period) {
				if (!userActivity.containsKey(userName))
					userActivity.put(userName, 0);
				userActivity.put(userName, userActivity.get(userName) + 1);
			}
		}
		List<String> usersMarksData = new ArrayList<String>();
		for (Entry<String, Integer> marksData : userActivity.entrySet()) {
			int marksCount = marksData.getValue();
			usersMarksData.add(Integer.toString(marksCount) + ": " + marksData.getKey());
		}
		Collections.sort(usersMarksData);
		Collections.reverse(usersMarksData);
		List<String> activeUsersList = new ArrayList<String>();
		int count = 0;
		for (String userMarksData : usersMarksData) {
			count++;
			String[] userData = userMarksData.split(": ", 2);
			activeUsersList.add(userData[1] + ": " + userData[0]);
			if (count == 10)
				break;
		}
		return activeUsersList;
	}
}
