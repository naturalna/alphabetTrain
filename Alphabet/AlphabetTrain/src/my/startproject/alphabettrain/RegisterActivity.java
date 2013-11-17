package my.startproject.alphabettrain;

import my.startproject.datalayer.UserSignupRequests;
import my.startproject.models.UserModel;
import my.testproject.allevents.ISignupListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements ISignupListener {

	private ProgressDialog dialog = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	private void turnOnProgressDialog(String title, String message) {
		this.dialog = ProgressDialog.show(this, title, message);
	}

	private void turnOffProgressDialog() {
		this.dialog.cancel();
	}

	private String getStringValue(int controlId) {
		TextView tv = (TextView) findViewById(controlId);
		if (tv == null) {
			throw new RuntimeException("Sorry Can't find the control id");
		}
		// view available
		return tv.getText().toString();
	}

	private String getUserId() {
		return getStringValue(R.id.userid);
	}

	private String getUserEmail() {
		return getStringValue(R.id.email);
	}

	private String getPassword1() {
		return getStringValue(R.id.password1);
	}

	public void signupButtonClick(View v) {
		try {
			String userid = getUserId();
			String email = getUserEmail();
			String password = getPassword1();

			this.Validate(userid, password, email);
			if (this.isValid) {
				UserModel user = new UserModel(userid, email, password);
				UserSignupRequests requests = new UserSignupRequests(
						RegisterActivity.this, user);
				requests.Signup();
				turnOnProgressDialog("Signup",
						"Please wait while we sign you up");
			} else {
				this.isValid = true;
				Toast.makeText(RegisterActivity.this,
						"Registration Faild.Incorrect username or password",
						Toast.LENGTH_LONG).show();
			}
		} catch (RuntimeException ex) {
			Toast.makeText(RegisterActivity.this, ex.toString(),
					Toast.LENGTH_LONG).show();
		}
	}

	private boolean isValid = true;

	private boolean Validate(String username, String password, String email) {
		String regex = "^[a-zA-Z]+$";

		if (username.length() < 5) {
			this.isValid = false;
		}
		if (!username.matches(regex)) {
			this.isValid = false;
		}
		if (password.length() < 5) {
			this.isValid = false;
		}
		if (!password.matches(regex)) {
			this.isValid = false;
		}
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if (!email.matches(emailreg)) {
			this.isValid = false;
		}
		return true;
	}

	@Override
	public void signupSucceed() {
		turnOffProgressDialog();
		Intent homeIntent = new Intent(RegisterActivity.this,
				HomeActivity.class);
		startActivity(homeIntent);
	}

	@Override
	public void signupFaild() {
		turnOffProgressDialog();
		Toast.makeText(RegisterActivity.this,
				"Registration Faild.Incorrect username or password",
				Toast.LENGTH_LONG).show();
	}
}
