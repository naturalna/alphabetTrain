package my.startproject.models;

public class UserModelByScores {
	private String username;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public UserModelByScores(String username) {
		setUsername(username);
	}
}
