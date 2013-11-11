package my.startproject.alphabettrain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import my.testproject.allevents.EventManegerLetterRecived;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TextRecognition {
	private static Context ctx;
	private String resultUrl = "result.txt";
	private String imageFilePath = null;
	TextView tv;
	private static HardFragment frag;
	EventManegerLetterRecived lettersRequest;

	public TextRecognition(Context context, HardFragment fr) {
		ctx = context;
		frag = fr;
		lettersRequest = new EventManegerLetterRecived();
	}

	public void getStartedTest(Bitmap bmp) {
		Uri imageUri = getOutputMediaFileUri(bmp);
		imageFilePath = imageUri.getPath();
		ctx.deleteFile(resultUrl);
		new AsyncProcessTask(frag).execute(this.imageFilePath, this.resultUrl);
	}

	public void updateResults() {
		try {
			
			lettersRequest.addListener(frag);
			StringBuffer contents = new StringBuffer();

			FileInputStream fis = ctx.openFileInput(resultUrl);
			Reader reader = new InputStreamReader(fis, "UTF-8");
			BufferedReader bufReader = new BufferedReader(reader);
			String text = null;
			while ((text = bufReader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}
			
			ResultString = contents.toString().trim().charAt(0) +"";
			
			Toast.makeText(ctx,contents.toString(), Toast.LENGTH_LONG).show();
			lettersRequest.saySucceed();
			lettersRequest.Clear();
			
			// displayMessage(contents.toString());
		} catch (Exception e) {
			// displayMessage("Error: " + e.getMessage());
		}
	}

	public String ResultString = "notReadyYet";

	private static Uri getOutputMediaFileUri(Bitmap bmp) {
		return Uri.fromFile(getOutputMediaFile(bmp));
	}

	private static File getOutputMediaFile(Bitmap bmp) {
		String file_path = "img.png";
		File file = new File(ctx.getFilesDir(), file_path);
		FileOutputStream fOut;

		try {
			fOut = new FileOutputStream(file);
			// Bitmap bmp1 = BitmapFactory.decodeResource(ctx.getResources(),
			// R.drawable.apple);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
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

	/*private void displayMessage(String text) {
		tv.post(new MessagePoster(text));
	}*/

	class MessagePoster implements Runnable {
		public MessagePoster(String message) {
			_message = message;
		}

		public void run() {
			tv.append(_message + "\n");
			// setContentView( tv );
		}

		private final String _message;
	}
}
