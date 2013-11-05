package my.startproject.alphabettrain;

import java.util.ArrayList;
import java.util.Locale;

import my.startproject.datalayer.CustomGridViewAdapter;
import my.startproject.datalayer.Item;
import my.startproject.datalayer.LetterRequests;
import my.testproject.allevents.IdownloadedImage;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;


public class LearnActivity extends BaseActivity implements IdownloadedImage, TextToSpeech.OnInitListener{
	
	GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridAdapter;
    private LetterRequests requester;
    private String text;
    private TextToSpeech tts;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn);
		
		FillGrid();
		
		tts = new TextToSpeech(this, this);
		tts.setLanguage(Locale.ENGLISH);
		tts.setPitch(1);
		tts.setSpeechRate(1);
	}
	
	private void FillGrid()
	{
		requester = new LetterRequests();
		requester.getImagecollection(LearnActivity.this);
			
        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.alphabet, gridArray);
        gridView.setAdapter(customGridAdapter);
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				text =(String)((TextView) v.findViewById(R.id.item_text)).getText();
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
	}
	@Override
	public void Faild() {
		// TODO Auto-generated method stub
		
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
}
