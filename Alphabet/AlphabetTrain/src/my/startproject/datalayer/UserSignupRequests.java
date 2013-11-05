package my.startproject.datalayer;
import my.startproject.models.UserModel;
import my.testproject.allevents.EventManagerSignup;
import my.testproject.allevents.ISignupListener;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class UserSignupRequests {

	private UserModel user;
	private final EventManagerSignup manager = new EventManagerSignup();

	public UserSignupRequests(ISignupListener lis, UserModel user)
	{
		this.manager.addListener(lis);
		this.user = user;
	}

	public void Signup()
	{
		ParseUser user = new ParseUser();
		user.setUsername(this.user.getUsername());
		user.setPassword(this.user.getPassword());
		user.setEmail(this.user.getEmail());

		user.signUpInBackground(new SignUpCallback() {
		  public void done(ParseException e) {
		    if (e == null) {
		      manager.saySucceed();
		    } else {
		      // Sign up didn't succeed. Look at the ParseException
		      // to figure out what went wrong
		    	manager.sayFaild();
		    }
		  }
		});		
	}
	
}
