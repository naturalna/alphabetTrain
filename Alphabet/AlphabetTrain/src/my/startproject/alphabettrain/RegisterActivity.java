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

	/*
	 * private String getPassword2() { return getStringValue(R.id.password2); }
	 */

	public void signupButtonClick(View v) {
		// TODO validation
		String userid = getUserId();
		String email = getUserEmail();
		String password = getPassword1();

		UserModel user = new UserModel(userid, email, password);
		UserSignupRequests requests = new UserSignupRequests(
				RegisterActivity.this, user);
		requests.Signup();
		turnOnProgressDialog("Signup", "Please wait while we sign you up");
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
		Toast.makeText(RegisterActivity.this, "RegistrationFaild.",
				Toast.LENGTH_LONG).show();
	}
}
