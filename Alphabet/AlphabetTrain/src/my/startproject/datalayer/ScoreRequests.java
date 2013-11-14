package my.startproject.datalayer;

import java.util.ArrayList;
import java.util.List;

import my.startproject.models.ScoreModel;
import my.startproject.models.UserModelByScores;
import my.startproject.models.UserScoreModel;
import my.testproject.allevents.EventManagerScoreReceived;
import my.testproject.allevents.IScoreReceived;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ScoreRequests {

	private ParseObject scoresObject = null;
	private ScoreModel uscoreModel = null;
	private int updateScores = 0;
	
	private final EventManagerScoreReceived managerScores = new EventManagerScoreReceived();

	public void getLastUserScores(IScoreReceived listener) {
		managerScores.addListener(listener);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Scores");
		query.whereEqualTo("User", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> scoreList, ParseException e) {
				if (e == null) {
					UserModelByScores user = new UserModelByScores(ParseUser
							.getCurrentUser().getUsername());

					if (scoreList.isEmpty()) {
						createUserScores(0, 1);
						uscoreModel = new ScoreModel(user, 0, 1);

					} else {
						scoresObject = scoreList.get(0);
						int currentScores = scoresObject.getInt("Points");
						int currentLevel = scoresObject.getInt("Level");

						uscoreModel = new ScoreModel(user, currentScores,
								currentLevel);
					}

					managerScores.saySucceed(uscoreModel);

				} else {
					managerScores.sayFaild(e.getMessage());
				}

				managerScores.Clear();
			}
		});
	}

	public void createUserScores(int score, int lv) {
		ParseObject gameScore = new ParseObject("Scores");
		gameScore.put("User", ParseUser.getCurrentUser());
		gameScore.put("Points", score);
		gameScore.put("Level", lv);
		gameScore.put("Username", ParseUser.getCurrentUser().getUsername());
		gameScore.saveInBackground();
	}
	
	private final EventManagerScoreReceived managerUpdateScores = new EventManagerScoreReceived();

	public void updateScores(IScoreReceived listener, int sc) {
		managerUpdateScores.addListener(listener);
		updateScores = sc;

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Scores");
		query.whereEqualTo("User", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> scoreList, ParseException e) {
				if (e == null) {
					UserModelByScores user = new UserModelByScores(ParseUser
							.getCurrentUser().getUsername());

					if (scoreList.isEmpty()) {
						createUserScores(updateScores, 1);
						uscoreModel = new ScoreModel(user, updateScores, 1);
					} else {
						scoresObject = scoreList.get(0);
						int currentScores = scoresObject.getInt("Points");
						int newScores = currentScores + updateScores;

						int currentLevel = currentScores / 100;

						// actual update
						scoresObject.put("Points", newScores);
						scoresObject.put("Level", currentLevel);
						scoresObject.saveInBackground();

						uscoreModel = new ScoreModel(user, newScores,
								currentLevel);
					}

					managerUpdateScores.saySucceed(uscoreModel);

				} else {
					managerUpdateScores.sayFaild(e.getMessage());

				}
				managerUpdateScores.Clear();
			}
		});
	}

	private final EventManagerScoreReceived managerScoresRanklist = new EventManagerScoreReceived();
	private List<UserScoreModel> list;

	public void getTopTenByScore(IScoreReceived listener) {
		managerScoresRanklist.addListener(listener);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Scores");
		query.orderByDescending("Points");
		query.setLimit(10);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> scoreList, ParseException e) {
				if (e == null) {
					list = new ArrayList<UserScoreModel>();
					for (ParseObject item : scoreList) {
						UserScoreModel model = new UserScoreModel(item
								.getString("Username"), item.getInt("Points"));
						list.add(model);
					}

					managerScoresRanklist.sayRecived(list);
				} else {
					managerUpdateScores.sayRecivedFaild(e.getMessage());
				}
				managerUpdateScores.Clear();
			}
		});
	}

	public void getUserScore(IScoreReceived listener) {
		managerScoresRanklist.addListener(listener);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Scores");
		query.whereEqualTo("User", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> scoreList, ParseException e) {
				if (e == null) {
					UserScoreModel model =null;
					if (scoreList.isEmpty()) {
						createUserScores(0, 0);
						model = new UserScoreModel(ParseUser.getCurrentUser().getUsername(), 0);
					} else {
						ParseObject item = scoreList.get(0);
						model = new UserScoreModel(item.getString("Username"), item.getInt("Points"));
					}
					managerScoresRanklist.sayRecivedUserScore(model);
				} else {
					managerUpdateScores.sayRecivedFaildUserScore(e.getMessage());
				}
				managerUpdateScores.Clear();
			}
		});
	}
}
