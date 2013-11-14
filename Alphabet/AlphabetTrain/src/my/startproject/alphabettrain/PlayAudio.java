package my.startproject.alphabettrain;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlayAudio extends Service{
	private static final String LOGCAT = null;
	MediaPlayer objPlayer;
 
	public void onCreate(){
		super.onCreate();
		Log.d(LOGCAT, "Service Started!");
		objPlayer = MediaPlayer.create(this, R.raw.jingle);
	}
 
	public int onStartCommand(Intent intent, int flags, int startId){
		
		objPlayer.setLooping(true);
		objPlayer.start();
		Log.d(LOGCAT, "Media Player started!");
		
		if(objPlayer.isLooping() != true){
			Log.d(LOGCAT, "Problem in Playing Audio");
		}
		return 1;
	}
	
	public void onDestroy(){
		objPlayer.stop();
		objPlayer.release();
	}
	
	@Override
	public IBinder onBind(Intent objIndent) {
		return null;
	}
	
}
