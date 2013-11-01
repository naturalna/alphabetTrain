package my.startproject.alphabettrain;

import java.util.List;

import my.startproject.datalayer.ScoreRequests;
import my.startproject.models.ScoreModel;
import my.startproject.models.UserScoreModel;
import my.testproject.allevents.IScoreReceived;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends BaseActivity implements IScoreReceived{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
		//TestRequests();

	}
	
	private void TestRequests()
	{
		//Test
		ScoreRequests requester = new ScoreRequests();
		requester.getLastUserScores(HomeActivity.this);
		requester.updateScores(HomeActivity.this, 15);
		requester.getTopTenByScore(HomeActivity.this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}
	
	//@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{	
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void scoreReceivedSucceed(ScoreModel model) {
		TextView tv = (TextView)findViewById(R.id.textView1);
		tv.setText(model.getPoints() + " Level: "+ model.getLevel());
	}

	@Override
	public void scoreReceivedFaild(String errorMessage) {
		Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
	}

	@Override
	public void rankListReceivedSucceed(List<UserScoreModel> model) {
		TextView tv = (TextView)findViewById(R.id.textView1);
		tv.setText("Recived : " +  model.size());
	}

	@Override
	public void rankListReceivedFaild(String errorMessage) {
		Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
	}
}
