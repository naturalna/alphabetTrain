package my.startproject.alphabettrain;

import java.util.ArrayList;
import java.util.List;

import my.startproject.datalayer.ScoreRequests;
import my.startproject.models.ScoreModel;
import my.startproject.models.UserScoreModel;
import my.testproject.allevents.IScoreReceived;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ProgressActivity extends ListActivity implements IScoreReceived {
	private List<String> values = new ArrayList<String>();
	private ScoreRequests requester = new ScoreRequests();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requester.getTopTenByScore(ProgressActivity.this);
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
	public void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(this, String.valueOf(l.getItemAtPosition(position)),
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void scoreReceivedSucceed(ScoreModel model) {
	}

	@Override
	public void scoreReceivedFaild(String errorMessage) {
		Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	}

	@Override
	public void rankListReceivedSucceed(List<UserScoreModel> model) {
		try {
			for (int i = 0; i < model.size(); i++) {
				UserScoreModel user = model.get(i);

				values.add("Points : " + user.getPoints() + " Username : "
						+ user.getUsername());
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					ProgressActivity.this, android.R.layout.simple_list_item_1,
					values);
			setListAdapter(adapter);
		} catch (Exception ex) {
			Toast.makeText(this, "Please restart and try again",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void rankListReceivedFaild(String errorMessage) {
		Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	}

	@Override
	public void scoreUserReceivedSucceed(UserScoreModel model) {
	}

	@Override
	public void scoreUserReceivedFaild(String errorMessage) {
		Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	}
}
