package my.startproject.alphabettrain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import my.startproject.datalayer.CacheLetters;
import my.startproject.datalayer.Item;
import my.startproject.datalayer.LetterRequests;
import my.startproject.datalayer.ScoreRequests;
import my.startproject.models.ScoreModel;
import my.startproject.models.UserScoreModel;
import my.testproject.allevents.ILetterRecived;
import my.testproject.allevents.IScoreReceived;
import my.testproject.allevents.ITestRecived;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HardFragment extends Fragment implements ITestRecived,
		IScoreReceived, ILetterRecived, OnTouchListener {

	private static SecureRandom random = new SecureRandom();
	private ScoreRequests requestScores = new ScoreRequests();

	private ArrayList<Item> listcache = new ArrayList<Item>();
	private ImageView imageView;
	private Bitmap bitmap;
	private Canvas canvas;
	private Paint paint;
	float downx = 0, downy = 0, upx = 0, upy = 0;
	private Path path = new Path();
	public TextRecognition textRecognition;
	private CacheLetters cache;
	private LetterRequests requester;
	public Item imageItem;
	private int randomPosition;
	private ProgressDialog dialog = null;
	private Activity activity;
	private Integer points = 0;
	private Button button;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.hard_fragment, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = this.getActivity();
		textRecognition = new TextRecognition(this.activity.getBaseContext(),
				this);

		DrawCanvasTest();
		CanvasImage();

		this.button = (Button) getActivity().findViewById(R.id.buttonTest);

		this.button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				button.setEnabled(false);
				textRecognition.getStartedTest(bitmap);
			}
		});
		Button buttonClear = (Button) getActivity().findViewById(
				R.id.clearCanvas);

		buttonClear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// bitmap.eraseColor(Color.BLACK);
				bitmap = Bitmap.createBitmap((int) 150, (int) 150,
						Bitmap.Config.ARGB_8888);
				canvas = new Canvas(bitmap);
				path = new Path();
				Paint clearPaint = new Paint();
				clearPaint.setXfermode(new PorterDuffXfermode(
						PorterDuff.Mode.CLEAR));
				canvas.drawRect(0, 0, 0, 0, clearPaint);

				canvas.drawColor(Color.WHITE);
				imageView = (ImageView) getActivity().findViewById(
						R.id.imgCanvas);
				imageView.setImageBitmap(bitmap);
			}
		});
	}

	public void CanvasImage() {
		imageView = (ImageView) getActivity().findViewById(R.id.imgCanvas);
		bitmap = Bitmap.createBitmap((int) 150, (int) 150,
				Bitmap.Config.ARGB_8888);
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

	private void DrawCanvasTest() {
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
		ImageView imgView = (ImageView) this.activity
				.findViewById(R.id.testImage);
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

	private void CalculatePoints(String result) {
		String wantedUpper = imageItem.getTitle();
		String wantedLower = wantedUpper.toLowerCase(Locale.ENGLISH);
		this.points = 0;

		if (result.contains(wantedUpper) || result.contains(wantedLower)) {
			this.points = 10;
		}

		String winPoints = "You win " + points + " points";
		turnOnProgressDialog("Points", winPoints);

		UpdatePoints();

		TextView points = (TextView) getActivity().findViewById(R.id.pointTV);
		int lastPoints = Integer.parseInt(points.getText().toString().trim());
		String pointsText = " " + (lastPoints + this.points);
		points.setText(pointsText);
	}

	private void UpdatePoints() {
		requestScores.updateScores(this, this.points);
	}

	@Override
	public void scoreReceivedSucceed(ScoreModel model) {
		turnOffProgressDialog();
		imageItem = null;
		DrawCanvasTest();

		bitmap = Bitmap.createBitmap((int) 150, (int) 150,
				Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);
		path = new Path();
		Paint clearPaint = new Paint();
		clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		canvas.drawRect(0, 0, 0, 0, clearPaint);
		canvas.drawColor(Color.WHITE);
		imageView = (ImageView) getActivity().findViewById(R.id.imgCanvas);
		imageView.setImageBitmap(bitmap);
		this.button.setEnabled(true);
	}

	@Override
	public void scoreReceivedFaild(String errorMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void rankListReceivedSucceed(List<UserScoreModel> model) {
		// TODO Auto-generated method stub
	}

	@Override
	public void rankListReceivedFaild(String errorMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void SucceedLettersRecived(String letter) {
		CalculatePoints(letter);
	}

	@Override
	public void FaildLettersRecived() {
		// TODO Auto-generated method stub

	}

	@Override
	public void scoreUserReceivedSucceed(UserScoreModel model) {
	}

	@Override
	public void scoreUserReceivedFaild(String errorMessage) {
		// TODO Auto-generated method stub

	}

}
