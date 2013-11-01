package my.startproject.models;

public class UserModelSingin {
	private String username;
	private String password;
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setPassword(String pass)
	{
		this.password = pass;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public UserModelSingin(String username, String password)
	{
		setUsername(username);
		setPassword(password);
	}
}
