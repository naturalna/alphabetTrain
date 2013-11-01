package my.startproject.alphabettrain;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProgressActivity extends BaseActivity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);
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
}
