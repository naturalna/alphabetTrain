package my.startproject.datalayer;

import my.startproject.models.UserModelReset;
import my.testproject.allevents.EventManagerReset;
import my.testproject.allevents.IPasswordReset;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class UserResetRequest {
	private UserModelReset user;

	private final EventManagerReset manager = new EventManagerReset();

	public UserResetRequest(IPasswordReset lis, UserModelReset user) {
		this.manager.addListener(lis);
		this.user = user;
	}

	public void Reset() {
		ParseUser.requestPasswordResetInBackground(this.user.getEmail(),
				new RequestPasswordResetCallback() {
					public void done(ParseException e) {
						if (e == null) {
							manager.saySucceed();
						} else {
							manager.sayFaild();
						}
					}
				});
	}
}
