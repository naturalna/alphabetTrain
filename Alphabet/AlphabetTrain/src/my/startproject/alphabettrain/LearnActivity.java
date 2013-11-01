package my.startproject.alphabettrain;

import java.util.ArrayList;

import my.startproject.datalayer.CustomGridViewAdapter;
import my.startproject.datalayer.Item;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;


public class LearnActivity extends BaseActivity{
	
	GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridAdapter;
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn);
		
		fillGrid();
	}
	private void fillGrid()
	{
		Bitmap apple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        Bitmap bee = BitmapFactory.decodeResource(this.getResources(), R.drawable.bee);
        Bitmap cherry = BitmapFactory.decodeResource(this.getResources(), R.drawable.cherry);
        
        gridArray.add(new Item(apple,"A"));
        gridArray.add(new Item(bee,"B"));
        gridArray.add(new Item(cherry,"C"));
        
        
        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.alphabet, gridArray);
        gridView.setAdapter(customGridAdapter);
	}
	/*private void fillGrid()
	{
		gridView = (GridView) findViewById(R.id.gridView1);
		 
		gridView.setAdapter(new ImageAdapter(this, MOBILE_OS));
 
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(
				   getApplicationContext(),
				   ((TextView) v.findViewById(R.id.grid_item_label))
				   .getText(), Toast.LENGTH_SHORT).show();
 
			}
		});
	}*/
	
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
