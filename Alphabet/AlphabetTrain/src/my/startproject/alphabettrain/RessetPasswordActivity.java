package my.startproject.alphabettrain;

import my.startproject.datalayer.UserResetRequest;
import my.startproject.models.UserModelReset;
import my.testproject.allevents.IPasswordReset;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RessetPasswordActivity extends Activity implements IPasswordReset {

	private ProgressDialog dialog = null;
	private EditText email;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		email = (EditText) findViewById(R.id.email);
	}

	public void resetPassword(View v) {
		String sEmail = email.getText().toString().trim();
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if (sEmail.matches(emailreg)) {

			UserModelReset user = new UserModelReset(sEmail);
			UserResetRequest request = new UserResetRequest(
					RessetPasswordActivity.this, user);
			request.Reset();
			turnOnProgressDialog("Reset",
					"Please wait while we reset your password");
		}
	}

	private void turnOnProgressDialog(String title, String message) {
		this.dialog = ProgressDialog.show(this, title, message);
	}

	private void turnOffProgressDialog() {
		this.dialog.cancel();
	}

	@Override
	public void resetSucceed() {
		turnOffProgressDialog();
		Intent homeIntent = new Intent(RessetPasswordActivity.this,
				SigninActivity.class);
		startActivity(homeIntent);
	}

	@Override
	public void resetFaild() {
		turnOffProgressDialog();
		Toast.makeText(RessetPasswordActivity.this,
				"Password was not reset. Place try again.", Toast.LENGTH_LONG)
				.show();
	}
}
