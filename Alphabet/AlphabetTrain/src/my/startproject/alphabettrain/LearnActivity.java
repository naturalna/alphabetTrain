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
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class LearnActivity extends BaseActivity implements IdownloadedImage, TextToSpeech.OnInitListener{
	
	private GridView gridView;
	private ArrayList<Item> gridArray = new ArrayList<Item>();
	private CustomGridViewAdapter customGridAdapter;
    private LetterRequests requester;
    private String text;
    private TextToSpeech tts;
    private ProgressDialog dialog = null;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn);
		
		FillGrid();
		try{
		tts = new TextToSpeech(this, this);
		tts.setLanguage(Locale.ENGLISH);
		tts.setPitch(1);
		tts.setSpeechRate(1);
		}catch(Exception ex){
			Toast.makeText(this, "Please restart and try again", Toast.LENGTH_LONG).show();
		}
	}
	
	private void FillGrid()
	{
		requester = new LetterRequests();
		requester.getImagecollection(LearnActivity.this);
		this.turnOnProgressDialog("Loading...","Wait while we load the training section.");
		try{	
        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.alphabet, gridArray);
        gridView.setAdapter(customGridAdapter);
		}
		catch(Exception ex){
			Toast.makeText(LearnActivity.this, ex.toString(),
					Toast.LENGTH_LONG).show();
		}
		
        gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				text =(String)((TextView) v.findViewById(R.id.item_text)).getText();
				ImageView image = (ImageView) v.findViewById(R.id.item_image);
				//animation test
				 // Step1 : create the  RotateAnimation object
		        RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
		        // Step 2:  Set the Animation properties
		        anim.setInterpolator(new LinearInterpolator());
		        anim.setRepeatCount(Animation.INFINITE);
		        //anim.setDuration(400);
		        // Step 3: Start animating the image
		        image.startAnimation(anim);
				speakOut();	
				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{	
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

	}
	
	@Override
    public void onInit(int status) { 
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
               // btnSpeak.setEnabled(true);
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
