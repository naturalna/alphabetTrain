package my.startproject.alphabettrain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

public class TextRecognition {
	private static Context ctx; 
	private String resultUrl = "result.txt";
	private String imageFilePath = null;
	TextView tv;
	private static HardFragment frag;
	
	public TextRecognition(Context context, HardFragment fr)
	{
		ctx = context;
		frag = fr;
		
		Uri imageUri = getOutputMediaFileUri();
		imageFilePath = imageUri.getPath();
		context.deleteFile(resultUrl);
	}
	
	public void getStartedTest()
	{
		new AsyncProcessTask(frag).execute(this.imageFilePath, this.resultUrl);
	}
	
	public void updateResults() {
		try {
			StringBuffer contents = new StringBuffer();

			FileInputStream fis = ctx.openFileInput(resultUrl);
			Reader reader = new InputStreamReader(fis, "UTF-8");
			BufferedReader bufReader = new BufferedReader(reader);
			String text = null;
			while ((text = bufReader.readLine()) != null) {
				contents.append(text).append(System.getProperty("line.separator"));
			}
			Toast.makeText(ctx,contents.toString() , Toast.LENGTH_LONG)
			.show();
			//displayMessage(contents.toString());
		} catch (Exception e) {
			//displayMessage("Error: " + e.getMessage());
		}
	}
	
	private static Uri getOutputMediaFileUri(){
	      return Uri.fromFile(getOutputMediaFile());
	}

	private static File getOutputMediaFile(){
		String file_path = "img.png";
		File file = new File(ctx.getFilesDir(), file_path);
		FileOutputStream fOut;
	
		try {
			fOut = new FileOutputStream(file);
			 Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.a);
				bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
				fOut.flush();
				fOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   return file;
	}

	private void displayMessage( String text )
	{
		tv.post( new MessagePoster( text ) );
	}
	
	class MessagePoster implements Runnable {
		public MessagePoster( String message )
		{
			_message = message;
		}

		public void run() {
			tv.append( _message + "\n" );
			//setContentView( tv );
		}

		private final String _message;
	}
}
