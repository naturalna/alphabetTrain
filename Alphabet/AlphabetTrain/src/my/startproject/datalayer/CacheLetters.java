package my.startproject.datalayer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class CacheLetters {

	private boolean areLettersCached;
	private LocalDataBase datasource;
	private Context context;
	private List<Item> items;

	public CacheLetters(Context context) {
		this.context = context;
		this.items = GetAll();

		FindCacheState();
	}

	private void FindCacheState() {
		// null pointer
		if (this.getItems().size() > 0) {
			this.setAreLettersCached(true);
		} else {
			this.setAreLettersCached(false);
		}
	}

	public void CreateDB(List<Item> allItems) {
		try {
			datasource = new LocalDataBase(this.context);
			datasource.open();

			for (Item i : allItems) {
				datasource.createLetter(i);
			}
			this.items = allItems;
		} catch (Exception ex) {
			// Toast.makeText(this, "SQLite error ", 300 );
			System.out.println("Exception! " + ex.toString());
		}
	}

	private List<Item> GetAll() {
		if (datasource == null) {
			datasource = new LocalDataBase(this.context);
		}
		try {
			datasource.open();
		} catch (Exception ex) {
			System.out.println("Exception! " + ex.toString());
		}

		List<Item> allItems = new ArrayList<Item>();
		try {
			allItems = datasource.getAllLetters();
		} catch (Exception ex) {
			System.out.println("Exception! " + ex.toString());
		}
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
