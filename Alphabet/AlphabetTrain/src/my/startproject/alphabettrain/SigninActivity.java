package my.startproject.alphabettrain;

import my.startproject.datalayer.UserLoginRequests;
import my.startproject.models.UserModelSingin;
import my.testproject.allevents.ISigninListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends Activity implements ISigninListener {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
	}

	EditText userid;
	EditText password;
	private ProgressDialog dialog = null;

	private String getUserid() {
		userid = (EditText) findViewById(R.id.userid);
		return userid.getText().toString();
	}

	private String getPassword() {
		password = (EditText) findViewById(R.id.password);
		return password.getText().toString();
	}

	public void login(View v) {
		// TODO validate and scripts
		String sUserid = getUserid();
		String sPassword = getPassword();
		UserModelSingin user = new UserModelSingin(sUserid, sPassword);

		turnOnProgressDialog("Login", "Wait while we log you in");
		UserLoginRequests requests = new UserLoginRequests(SigninActivity.this,
				user);
		requests.Signin();
	}

	private void turnOnProgressDialog(String title, String message) {
		this.dialog = ProgressDialog.show(this, title, message);
	}

	private void turnOffProgressDialog() {
		this.dialog.cancel();
	}

	@Override
	public void signinSucceed() {
		this.turnOffProgressDialog();
		Intent homeIntent = new Intent(SigninActivity.this, HomeActivity.class);
		startActivity(homeIntent);
	}

	@Override
	public void signinFaild() {
		turnOffProgressDialog();
		Toast.makeText(SigninActivity.this, "Login faild!", Toast.LENGTH_LONG)
				.show();

	}

	public void resetPassword(View v) {
		Intent homeIntent = new Intent(SigninActivity.this,
				RessetPasswordActivity.class);
		startActivity(homeIntent);
	}
}
