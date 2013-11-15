package my.startproject.alphabettrain;

import java.util.List;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;

import my.startproject.datalayer.ScoreRequests;
import my.startproject.models.ScoreModel;
import my.startproject.models.UserScoreModel;
import my.startproject.utilities.Typewriter;
import my.testproject.allevents.IScoreReceived;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends BaseActivity implements IScoreReceived {

	private ScoreRequests requester = new ScoreRequests();
	private Typewriter writer;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		InitializeParse();
		requester.getUserScore(this);
		
	}
	
	private void InitializeParse()
	{
				Parse.initialize(this, "QWbsGCAcrzn8p7OCjnX4K4MJFPTJQm62n8sHhnoc", 
						"6CoYnYg8ECKdqkJJThlnK8N1tNp0IAs0x0RDLxTw"); 
				ParseAnalytics.trackAppOpened(getIntent());
				
				// This allows read access to all objects
				ParseACL defaultACL = new ParseACL();
				defaultACL.setPublicReadAccess(true);
				ParseACL.setDefaultACL(defaultACL, true);
				Log.d("ParseApplication", "initializing app complete");
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	// @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void scoreReceivedSucceed(ScoreModel model) {
		TextView tv = (TextView) findViewById(R.id.textView1);
		
		tv.setText(model.getPoints() + " Level: " + model.getLevel());
	}

	@Override
	public void scoreReceivedFaild(String errorMessage) {
		Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void rankListReceivedSucceed(List<UserScoreModel> model) {
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText("Recived : " + model.size());
	}

	@Override
	public void rankListReceivedFaild(String errorMessage) {
		Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void scoreUserReceivedSucceed(UserScoreModel model) {
		try{
		TextView helloUser = (TextView) this.findViewById(R.id.helloTV);
		String greatings = "Hello, "+model.getUsername();
		//helloUser.setText(greatings);
		writer = new Typewriter(this,helloUser);
		writer.setCharacterDelay(100);
	    writer.animateText(greatings);
	    
		TextView points = (TextView) this.findViewById(R.id.pointTV);
		String pointsText = "Points: "+ model.getPoints();
		//points.setText(pointsText);
		writer = new Typewriter(this,points);
		writer.setCharacterDelay(100);
	    writer.animateText(pointsText);
	    
		}
		catch(Exception ex )
		{
			System.out.println("Exception thrown  :" + ex);
			Toast.makeText(HomeActivity.this, "Error! Please restart and try again.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void scoreUserReceivedFaild(String errorMessage) {
		Toast.makeText(HomeActivity.this, errorMessage,
				Toast.LENGTH_LONG).show();
	}
}
