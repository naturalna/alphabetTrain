package my.startproject.alphabettrain;

import java.util.List;

import my.startproject.datalayer.MySQLiteHelper;
import my.startproject.datalayer.ScoreRequests;
import my.startproject.models.ScoreModel;
import my.startproject.models.UserScoreModel;
import my.startproject.utilities.Typewriter;
import my.testproject.allevents.IScoreReceived;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TestsActivity extends BaseActivity implements IScoreReceived {
	private ScoreRequests requestUserScores = new ScoreRequests();
	private FragmentTransaction fragmentTransaction;
	private FragmentManager fragmentManager;
	private EasyFragment fragmentEasy;
	private HardFragment fragmentHard;
	private Typewriter writer;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tests);

		requestUserScores.getUserScore(this);

		this.fragmentManager = getFragmentManager();
		this.fragmentHard = new HardFragment();
		this.fragmentEasy = new EasyFragment();

		if (savedInstanceState == null) {
			try {
				initEasyFragment();
				initHardFragment();
			} catch (Exception ex) {
				Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}

	private void initHardFragment() {

		this.fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, fragmentHard);
		fragmentTransaction.detach(this.fragmentHard);
		fragmentTransaction.commit();
	}

	private void initEasyFragment() {

		this.fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, fragmentEasy);
		fragmentTransaction.detach(this.fragmentEasy);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	public void onToggleClicked(View view) {
		// Is the toggle on?
		boolean on = ((ToggleButton) view).isChecked();

		if (on) {
			// dropDB();
			try {
				this.fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.detach(this.fragmentHard);
				fragmentTransaction.attach(this.fragmentEasy);
				fragmentTransaction.commit();
			} catch (Exception ex) {
				Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
			}
		} else {
			try {
				this.fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.detach(this.fragmentEasy);
				fragmentTransaction.attach(this.fragmentHard);
				fragmentTransaction.commit();
			} catch (Exception ex) {
				Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}

	public void dropDB() {
		this.deleteDatabase(MySQLiteHelper.DATABASE_NAME);
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
	}

	@Override
	public void rankListReceivedFaild(String errorMessage) {
		Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	}

	@Override
	public void scoreUserReceivedSucceed(UserScoreModel model) {
		try {
			TextView helloUser = (TextView) findViewById(R.id.helloTV);
			String greatings = "Hello, " + model.getUsername();
			// helloUser.setText(greatings);
			writer = new Typewriter(this, helloUser);
			writer.setCharacterDelay(100);
			writer.animateText(greatings);

			TextView pointsTextView = (TextView) findViewById(R.id.textLabel);
			String pointsGreating = "Points:";
			writer = new Typewriter(this, pointsTextView);
			writer.setCharacterDelay(100);
			writer.animateText(pointsGreating);

			TextView points = (TextView) findViewById(R.id.pointTV);
			String pointsText = " " + model.getPoints();
			points.setText(pointsText);
		} catch (Exception ex) {
			Toast.makeText(this, "User points not calculate! Please restart!",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void scoreUserReceivedFaild(String errorMessage) {
		Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	}
}
