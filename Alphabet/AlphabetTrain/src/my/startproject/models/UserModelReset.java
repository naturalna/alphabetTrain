package my.startproject.models;

public class UserModelReset {
	private String email;

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public UserModelReset(String email) {
		setEmail(email);
	}
}
