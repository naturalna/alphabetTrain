package my.startproject.datalayer;

import com.parse.ParseUser;

public class UserLogout {
	public static void logout()
	{
		ParseUser.logOut();
	}
}
