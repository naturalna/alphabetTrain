package my.startproject.alphabettrain;

import java.util.ArrayList;
import java.util.Locale;
import my.startproject.datalayer.CustomGridViewAdapter;
import my.startproject.datalayer.Item;
import my.startproject.datalayer.LetterRequests;
import my.testproject.allevents.IdownloadedImage;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LearnActivity extends BaseActivity implements IdownloadedImage,
		TextToSpeech.OnInitListener {
	private ArrayList<Item> gridArray = new ArrayList<Item>();
	private ProgressDialog dialog = null;
	private CustomGridViewAdapter customGridAdapter;
	private LetterRequests requester;
	private GridView gridView;
	private String text;
	private TextToSpeech tts;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn);

		FillGrid();
		try {
			tts = new TextToSpeech(this, this);
			tts.setLanguage(Locale.ENGLISH);
			tts.setPitch(1);
			tts.setSpeechRate(1);

		} catch (Exception ex) {
			Toast.makeText(this, "Please restart and try again" + ex,
					Toast.LENGTH_LONG).show();
		}
	}

	private void FillGrid() {
		this.turnOnProgressDialog("Loading...",
				"Wait while we load the training section.");
		requester = new LetterRequests();
		requester.getImagecollection(LearnActivity.this);

		try {
			gridView = (GridView) findViewById(R.id.gridView1);
			customGridAdapter = new CustomGridViewAdapter(this,
					R.layout.alphabet, gridArray);
			gridView.setAdapter(customGridAdapter);
		} catch (Exception ex) {
			Toast.makeText(LearnActivity.this, ex.toString(), Toast.LENGTH_LONG)
					.show();
		}

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				text = (String) ((TextView) v.findViewById(R.id.item_text))
						.getText();
				ImageView image = (ImageView) v.findViewById(R.id.item_image);
				image.startAnimation(selectanimation((int) (Math.random() * 6)));
				speakOut();
			}
		});
	}

	@SuppressWarnings("static-access")
	private Animation selectanimation(int index) {
		switch (index) {
		case 0:
			return new AnimationUtils().loadAnimation(this, R.anim.shake);
		case 1:
			return new AnimationUtils().loadAnimation(this, R.anim.fade);
		case 2:
			return new AnimationUtils().loadAnimation(this, R.anim.slide_left);
		case 3:
			return new AnimationUtils().loadAnimation(this, R.anim.wave_scale);
		case 4:
			return new AnimationUtils().loadAnimation(this, R.anim.hold);
		case 5:
			return new AnimationUtils().loadAnimation(this, R.anim.zoom_enter);
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void Succeed(Item item) {
		gridArray.add(item);
		customGridAdapter.notifyDataSetChanged();
		this.turnOffProgressDialog();
	}

	@Override
	public void Faild() {
		this.turnOffProgressDialog();
		Toast.makeText(LearnActivity.this, "no internet connection",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {
				speakOut();
			}
		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	private void speakOut() {
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onDestroy() {
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	private void turnOnProgressDialog(String title, String message) {
		this.dialog = ProgressDialog.show(this, title, message);
	}

	private void turnOffProgressDialog() {
		this.dialog.cancel();
	}
}
