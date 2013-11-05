package my.startproject.alphabettrain;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

public class TestsActivity extends BaseActivity  {
	private FragmentTransaction fragmentTransaction;
	private FragmentManager fragmentManager;
	private EasyFragment fragmentEasy;
	private HardFragment fragmentHard;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tests);
		
		this.fragmentManager = getFragmentManager();
		this.fragmentHard = new HardFragment();
		this.fragmentEasy = new EasyFragment();
		
		if (savedInstanceState == null) { 
			initEasyFragment();  
			initHardFragment();
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
	
	//@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{	
		return super.onOptionsItemSelected(item);
	}
	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
	    boolean on = ((ToggleButton) view).isChecked();
	    
	    if (on) {
	    	 	this.fragmentTransaction = fragmentManager.beginTransaction();
	    	 	fragmentTransaction.detach(this.fragmentHard);
	    	 	fragmentTransaction.attach(this.fragmentEasy);
		        //fragmentTransaction.add(R.id.fragment_container, fragmentEasy);
		        fragmentTransaction.commit();
	    } else {
	    	this.fragmentTransaction = fragmentManager.beginTransaction();
	    	fragmentTransaction.detach(this.fragmentEasy);
	    	fragmentTransaction.attach(this.fragmentHard);
	    	fragmentTransaction.commit();
	    }
	}
	
	
	
}
