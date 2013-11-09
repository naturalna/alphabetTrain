package my.startproject.alphabettrain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import my.startproject.datalayer.CacheLetters;
import my.startproject.datalayer.CustomGridViewAdapter;
import my.startproject.datalayer.Item;
import my.startproject.datalayer.LetterRequests;
import my.testproject.allevents.ITestRecived;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HardFragment extends Fragment implements ITestRecived, OnTouchListener{
	//test
	  ImageView imageView;
	  Bitmap bitmap;
	  Canvas canvas;
	  Paint paint;
	  float downx = 0, downy = 0, upx = 0, upy = 0;
	  private Path path = new Path();
	 //test
	public TextRecognition textRecognition;
	private CacheLetters cache;
	private LetterRequests requester;
	private ArrayList<Item> listcache = new ArrayList<Item>();
	public Item imageItem;
	private int randomPosition;
	private static SecureRandom random = new SecureRandom();
	private ProgressDialog dialog = null;
	private Activity activity;
	private String result;
	private Integer points;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 super.onCreateView(inflater,container,savedInstanceState);
		 	View view = inflater.inflate(R.layout.hard_fragment,
	        container, false);
	    return view;
	  }
	 
	 @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		 super.onActivityCreated(savedInstanceState);
		 activity = this.getActivity();
		 textRecognition = new TextRecognition(this.activity.getBaseContext(), this);
		 
		//here view content
		 DrowCanvasTest();
		 CanvasImage();
		 
		 Button button = (Button) getActivity().findViewById(R.id.buttonTest);

		 button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					//from canvas 
					//Bitmap bitmapFromCanvas = GetImageFromCanvas();
					ImageView imgView =(ImageView) getActivity().findViewById(R.id.doublePic);
					textRecognition.getStartedTest(bitmap, imgView);
					
					result = textRecognition.ResultString;
					CalculatePoints(imageItem.getTitle(), result);
				}
			});
	}
	 
	 public void CanvasImage()
	  {	 
		    imageView = (ImageView) getActivity().findViewById(R.id.imgCanvas);
		    bitmap = Bitmap.createBitmap((int) 100, (int) 100, Bitmap.Config.ARGB_8888);
		    canvas = new Canvas(bitmap);
		    canvas.drawColor(Color.WHITE);
		    paint = new Paint();
		    paint.setColor(Color.BLACK);
		    
		    paint.setStyle(Paint.Style.STROKE);
		    paint.setStrokeJoin(Paint.Join.ROUND);
		    paint.setStrokeCap(Paint.Cap.ROUND);
		    paint.setStrokeWidth(12);
		    
		    imageView.setImageBitmap(bitmap);
		    
		    imageView.setOnTouchListener(this);
	  }
	 
	 @Override
	 public boolean onTouch(View v, MotionEvent event) {
		 float eventX = event.getX();
		    float eventY = event.getY();

		    switch (event.getAction()) {
		    case MotionEvent.ACTION_DOWN:
		      path.moveTo(eventX, eventY);
		      return true;
		    case MotionEvent.ACTION_MOVE:
		      path.lineTo(eventX, eventY);
		      canvas.drawPath(path, paint);
		      break;
		    case MotionEvent.ACTION_UP:
		      // nothing to do
		    	
		    	
		      break;
		    default:
		      return false;
		    }

		    // Schedules a repaint.
		    imageView.invalidate();
		    return true;
		  }
	 
	 private void DrowCanvasTest()
	 {
		 cache = new CacheLetters(this.getActivity());
		 CreateTest(); 
	 }
	 
	 public void CreateTest() {			
			CreateRandomPosition();
			
			if (cache.getAreLettersCached() == false) {
				
				turnOnProgressDialog("Download", 
						"Since this is your first test it will take a few seconds before starting.");
				requester = new LetterRequests();
				requester.getImagecollectionTest(HardFragment.this);
			} else {
				listcache = (ArrayList<Item>) this.cache.getItems();
				FillRandomLetters(listcache);
			}
		}

		private void CreateRandomPosition() {
			randomPosition = random.nextInt(21);
		}

		@Override
		public void Succeed(ArrayList<Item> list) {
			turnOffProgressDialog();
			this.cache.CreateDB(list);
			listcache.addAll(list);
			this.FillRandomLetters(list);
			
		}

		@Override
		public void Faild() {
			// TODO Auto-generated method stub
			
		}
		
		private void FillRandomLetters(ArrayList<Item> list) {	
				this.imageItem = list.get(randomPosition);
				ImageView imgView =(ImageView) this.activity.findViewById(R.id.testImage);
				imgView.setImageBitmap(this.imageItem.getImage());
		}

		@Override
		public void onDetach() {
			super.onDetach();
		}
		
		private void turnOnProgressDialog(String title, String message) {
			this.dialog = ProgressDialog.show(getActivity(), title, message);
		}

		private void turnOffProgressDialog() {
			this.dialog.cancel();
		}

		private void CalculatePoints(String wanted, String drawen)
		{
			if(wanted.equals(drawen))
			{
				points = 10;
			}
			//TODO update points
		}

}
