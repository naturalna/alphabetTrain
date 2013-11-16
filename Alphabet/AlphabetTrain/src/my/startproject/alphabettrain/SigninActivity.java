package my.startproject.alphabettrain;

import my.startproject.datalayer.UserLoginRequests;
import my.startproject.models.UserModelSingin;
import my.testproject.allevents.ISigninListener;
import android.app.Activity;
import android.app.ActivityOptions;
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

	private EditText userid;
	private EditText password;
	private Boolean isValid = true;
	private ProgressDialog dialog = null;

	private String getUserid() {
		userid = (EditText) findViewById(R.id.userid);
		return userid.getText().toString().trim();
	}

	private String getPassword() {
		password = (EditText) findViewById(R.id.password);
		return password.getText().toString().trim();
	}
	
	private boolean Validate(String username, String password)
	{
		String regex = "^[a-zA-Z]+$";
		
		if(username.length() < 5)
		{
			this.isValid = false;
		}
		if(!username.matches(regex)){
			this.isValid = false;
		}
		if(password.length() < 5)
		{
			this.isValid = false;
		}
		if(!password.matches(regex)){
			this.isValid = false;
		}
		
		return true;
	}

	private View view;
	public void login(View v) {
		view = v;
		String sUserid = getUserid();
		String sPassword = getPassword();
		
		this.Validate(sUserid, sPassword);
		if(this.isValid){
		UserModelSingin user = new UserModelSingin(sUserid, sPassword);

		turnOnProgressDialog("Login", "Wait while we log you in");
		UserLoginRequests requests = new UserLoginRequests(SigninActivity.this,
				user);
		requests.Signin();
		}else{
			this.isValid = true;
			Toast.makeText(this,"Login faild!Incorrect username or password", Toast.LENGTH_LONG).show();
		}
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
		//startActivity(homeIntent);
		ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
				0, view.getWidth(), view.getHeight());
		startActivity(homeIntent, options.toBundle());
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
		ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v, 0,
				0, v.getWidth(), v.getHeight());
		startActivity(homeIntent, options.toBundle());
		//startActivity(homeIntent);
	}
}
