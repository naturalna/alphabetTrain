package my.startproject.datalayer;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;


public class LocalDataBase {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_IMAGE };

	  public LocalDataBase(Context context) {
		//for dropping database 
	    dbHelper = new MySQLiteHelper(context);
	    
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public void createLetter(Item item ) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_NAME, item.getTitle());
	    values.put(MySQLiteHelper.COLUMN_IMAGE, item.getBitmapAsByteArray());
	    long insertId = database.insert(MySQLiteHelper.TABLE_LETTERS, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_LETTERS,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	   // cursor.moveToFirst();
	   // Comment newComment = cursorToComment(cursor);
	    cursor.close();
	   // return newComment;
	  }
	  
	  private Item cursorToItem(Cursor cursor) {
		  	Item item = new Item();
		  	item.setId(cursor.getLong(0));
		  	item.setTitle(cursor.getString(1));
		    byte[] img = cursor.getBlob(2);
		    item.setImage(BitmapFactory.decodeByteArray(img, 0, img.length));
		    return item;
	  }

	  public List<Item> getAllLetters() {
	    List<Item> items = new ArrayList<Item>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_LETTERS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Item comment = cursorToItem(cursor);
	      items.add(comment);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return items;
	  }
}
