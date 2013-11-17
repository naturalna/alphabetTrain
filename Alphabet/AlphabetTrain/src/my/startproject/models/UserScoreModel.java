package my.startproject.models;

public class UserScoreModel {
	private String username;
	private int points;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public UserScoreModel(String username, int points) {
		setUsername(username);
		setPoints(points);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
