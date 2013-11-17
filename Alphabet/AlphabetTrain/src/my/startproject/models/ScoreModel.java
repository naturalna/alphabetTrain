package my.startproject.models;

public class ScoreModel {
	private UserModelByScores user;
	private int points;
	private int level;

	public void setUser(UserModelByScores user) {
		this.user = user;
	}

	public UserModelByScores getUser() {
		return this.user;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return this.points;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return this.level;
	}

	public ScoreModel(UserModelByScores user, int point, int level) {
		setUser(user);
		setPoints(point);
		setLevel(level);
	}
}
