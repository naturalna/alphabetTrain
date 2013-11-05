package my.startproject.datalayer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class CacheLetters {
	
	private boolean areLettersCached;
	private LocalDataBase datasource;
	private Context context;
	private List<Item> items;
	
	public CacheLetters(Context context)
	{
		this.context = context;
		this.items = GetAll();
		
		FindCacheState();
	}

	private void FindCacheState() {
		//null pointer 
		if(this.getItems().size() > 0)
		{
			this.setAreLettersCached(true);
		}else{
			this.setAreLettersCached(false);
		}
	}
	
	public void CreateDB(List<Item> allItems)
	{
		datasource = new LocalDataBase(this.context);
	    datasource.open();
	    
	    for(Item i : allItems)
	    {
	    	datasource.createLetter(i);
	    }
	    this.items = allItems;
	   // Bitmap bee = BitmapFactory.decodeResource(this.getResources(), R.drawable.bee);
	   // Item i1 = new Item(bee,"B");
	    //Item i2 = new Item(bee,"A");
	  // datasource.createLetter(i1);
	}
	
	private List<Item> GetAll()
	{
		if(datasource == null)
		{
			datasource = new LocalDataBase(this.context);
		}
		
		datasource.open();
		List<Item> allItems = new ArrayList<Item>();
		allItems = datasource.getAllLetters();
		return allItems;
	}

	public List<Item> getItems() {
		return items;
	}

	public boolean getAreLettersCached() {
		return areLettersCached;
	}

	public void setAreLettersCached(boolean areLettersCached) {
		this.areLettersCached = areLettersCached;
	}
}
