package my.startproject.alphabettrain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity 
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//@Override
		public boolean onOptionsItemSelected(MenuItem item)
		{	
			switch (item.getItemId()) {
				case R.id.buttonTests:
					Intent testsIntent = new Intent(this, TestsActivity.class);
					startActivity(testsIntent);
				return true;
				case R.id.buttonLearn:
					Intent learnIntent = new Intent(this, LearnActivity.class);
					// 0 - parameter send to LearnActivity.class in this case nothing
					// data on which the action will be executed
					//startActivityForResult(learnIntent,0);
					
					startActivity(learnIntent);
				return true;
				case R.id.buttonProgress:
					Intent progressIntent = new Intent(this, ProgressActivity.class);
					startActivity(progressIntent);
				return true;
				case R.id.buttonPlay:
					Intent objIntent = new Intent(BaseActivity.this, PlayAudio.class);
			    	startService(objIntent); 
					return true;
				case R.id.buttonStop:
					Intent objIntent1 = new Intent(BaseActivity.this, PlayAudio.class);
			    	stopService(objIntent1); 
					return true;
				
				default:
					return super.onOptionsItemSelected(item);
			}
			}
}
