package my.startproject.datalayer;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import my.startproject.models.UserModelSingin;
import my.testproject.allevents.EventManagerSignin;
import my.testproject.allevents.ISigninListener;

public class UserLoginRequests {
	private UserModelSingin userLogin;
	private final EventManagerSignin managerLogin = new EventManagerSignin();
	
	public UserLoginRequests(ISigninListener lis, UserModelSingin user)
	{
		this.managerLogin.addListener(lis);
		this.userLogin = user;
	}
	
	public void Signin()
	{
		ParseUser.logInInBackground(this.userLogin.getUsername(),
				this.userLogin.getPassword(),
				new LogInCallback() {
			  		public void done(ParseUser user, ParseException e) {
				    if (user != null) {
				      managerLogin.saySucceed();
				    } else {
				      managerLogin.sayFaild();
				    }
			  	}
			});	
	}
}
