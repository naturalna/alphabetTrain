package my.startproject.datalayer;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class Item {
	private Bitmap image;
    private String title;
    private long id;
    
    public Item()
    {
    	super();
    }
    
    public Item(Bitmap image, String title) {
            super();
            this.image = image;
            this.title = title;
    }
    
    public Bitmap getImage() {
            return image;
    }
    
    public void setImage(Bitmap image) {
            this.image = image;
    }
    
    public String getTitle() {
            return title;
    }

    public void setTitle(String title) {
            this.title = title;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public byte[] getBitmapAsByteArray() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(CompressFormat.PNG, 0, outputStream);       
        return outputStream.toByteArray();
    }
}
