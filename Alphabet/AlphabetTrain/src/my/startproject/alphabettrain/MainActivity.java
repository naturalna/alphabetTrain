package my.startproject.alphabettrain;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.InitializeParse();
	}

	private void InitializeParse() {
		// Parse initialize
		Parse.initialize(this, "QWbsGCAcrzn8p7OCjnX4K4MJFPTJQm62n8sHhnoc",
				"6CoYnYg8ECKdqkJJThlnK8N1tNp0IAs0x0RDLxTw");
		ParseAnalytics.trackAppOpened(getIntent());

		// This allows read access to all objects
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		Log.d("ParseApplication", "initializing app complete");

	}

	public void registerClick(View view) {
		Intent homeIntent = new Intent(MainActivity.this,
				RegisterActivity.class);
		ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
				0, view.getWidth(), view.getHeight());
		// startActivity(homeIntent);
		startActivity(homeIntent, options.toBundle());
	}

	public void loginClick(View view) {
		Intent homeIntent = new Intent(MainActivity.this, SigninActivity.class);
		ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
				0, view.getWidth(), view.getHeight());
		// startActivity(homeIntent);
		startActivity(homeIntent, options.toBundle());
	}

}
