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

	public Statistics(VotesDatabase votesDatabase) throws IOException {
		this.votesDatabase = votesDatabase;
		votes = this.votesDatabase.getAllVotes();
	}

	public List<String> getMostActiveLikeUsers() {
		Map<String, Integer> userLikes = new HashMap<String, Integer>();
		for (Vote vote : votes) {
			String userName = vote.getUserId();
			if (!userLikes.containsKey(userName))
				userLikes.put(userName, 0);
			if (vote.isLike())
				userLikes.put(userName, userLikes.get(userName) + 1);
		}
		List<String> usersLikesData = new ArrayList<String>();
		for (Entry<String, Integer> likesData : userLikes.entrySet()) {
			int likesCount = likesData.getValue();
			if (likesCount == 0)
				continue;
			usersLikesData.add(Integer.toString(likesCount) + ": " + likesData.getKey());
		}
		Collections.sort(usersLikesData);
		Collections.reverse(usersLikesData);
		List<String> likeUsersList = new ArrayList<String>();
		int count = 0;
		for (String userLikesData : usersLikesData) {
			count++;
			String[] likesData = userLikesData.split(": ", 2);
			likeUsersList.add(likesData[1] + ": " + likesData[0]);
			if (count == 10)
				break;
		}
		return likeUsersList;
	}

	public List<String> getMostActiveDislikeUsers() {
		Map<String, Integer> userDislikes = new HashMap<String, Integer>();
		for (Vote vote : votes) {
			String userName = vote.getUserId();
			if (!userDislikes.containsKey(userName))
				userDislikes.put(userName, 0);
			if (!vote.isLike())
				userDislikes.put(userName, userDislikes.get(userName) + 1);
		}
		List<String> usersDislikesData = new ArrayList<String>();
		for (Entry<String, Integer> dislikesData : userDislikes.entrySet()) {
			int dislikesCount = dislikesData.getValue();
			if (dislikesCount == 0)
				continue;
			usersDislikesData.add(Integer.toString(dislikesCount) + ": " + dislikesData.getKey());
		}
		Collections.sort(usersDislikesData);
		Collections.reverse(usersDislikesData);
		List<String> dislikeUsersList = new ArrayList<String>();
		int count = 0;
		for (String userDislikesData : usersDislikesData) {
			count++;
			String[] dislikesData = userDislikesData.split(": ", 2);
			dislikeUsersList.add(dislikesData[1] + ": " + dislikesData[0]);
			if (count == 10)
				break;
		}
		return dislikeUsersList;
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
			String userName = vote.getUserId();
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
