package my.startproject.models;

public class UserModel {
	
	private String username;
	private String email;
	private String password;
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public void setPassword(String pass)
	{
		this.password = pass;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public UserModel(String username, String email, String password)
	{
		setUsername(username);
		setEmail(email);
		setPassword(password);
	}
}
