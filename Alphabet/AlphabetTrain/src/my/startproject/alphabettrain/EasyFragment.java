package my.startproject.alphabettrain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import my.startproject.datalayer.CacheLetters;
import my.startproject.datalayer.CustomGridViewAdapter;
import my.startproject.datalayer.Item;
import my.startproject.datalayer.LetterRequests;
import my.startproject.datalayer.ScoreRequests;
import my.startproject.models.ScoreModel;
import my.startproject.models.UserScoreModel;
import my.testproject.allevents.IScoreReceived;
import my.testproject.allevents.ITestRecived;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class EasyFragment extends Fragment implements ITestRecived,
		IScoreReceived {

	private CacheLetters cache;
	private List<Integer> positions;
	private static SecureRandom random = new SecureRandom();
	private GridView gridView;
	private ArrayList<Item> randomArray = new ArrayList<Item>();
	private CustomGridViewAdapter customGridAdapter;
	private LetterRequests requester;
	private ScoreRequests requestScores = new ScoreRequests();
	private ArrayList<Item> listcache = new ArrayList<Item>();
	private Button button;
	private ProgressDialog dialog = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.easy_fragment, container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		cache = new CacheLetters(this.getActivity());
		FillGrid();

		this.button = (Button) getActivity().findViewById(R.id.buttonReady);

		this.button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				button.setEnabled(false);
				CalcPoints();
			}
		});
	}

	public void FillGrid() {
		try{
		randomArray.clear();
		CreateRandomTest();
		gridView = (GridView) getActivity().findViewById(R.id.testGrid);
		customGridAdapter = new CustomGridViewAdapter(getActivity(),
				R.layout.easy_grid, randomArray);
		gridView.setAdapter(customGridAdapter);

		if (cache.getAreLettersCached() == false) {

			turnOnProgressDialog("Download",
					"Since this is your first test it will take a few seconds before starting.");
			requester = new LetterRequests();
			requester.getImagecollectionTest(EasyFragment.this);
		} else {

			listcache = (ArrayList<Item>) this.cache.getItems();
			FillRandomLetters(listcache);
		}
		} catch (Exception ex) {
			Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void CreateRandomTest() {
		positions = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			positions.add(random.nextInt(21));
		}
	}

	@Override
	public void Faild() {
		Toast.makeText(getActivity(), "Error in getting all letters",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void Succeed(ArrayList<Item> list) {
		turnOffProgressDialog();
		this.cache.CreateDB(list);
		listcache.addAll(list);
		this.FillRandomLetters(list);
	}

	private void FillRandomLetters(ArrayList<Item> list) {
		for (int index : positions) {
			randomArray.add(list.get(index));
		}

		customGridAdapter.notifyDataSetChanged();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		randomArray.clear();
	}

	private int points = 0;
	
	private void CalcPoints() {
		try{
		for (int i = 0; i < gridView.getChildCount(); i++) {
			View child = gridView.getChildAt(i);
			EditText editText = (EditText) child
					.findViewById(R.id.user_response);
			String text = editText.getText().toString();

			//text = text.toUpperCase(Locale.ENGLISH);
			if (text.equalsIgnoreCase(randomArray.get(i).getTitle())) {
				this.points += 2;
			}

			editText.setText("");
		}
		String winPoints = "You win " + points + " points";
		turnOnProgressDialog("Points", winPoints);
		
		UpdatePoints();
		fillTextviewPoints();
		
		} catch (Exception ex) {
			Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void fillTextviewPoints() {
		TextView points = (TextView) getActivity().findViewById(R.id.pointTV);
		String txt = points.getText().toString();
		txt = txt.trim();
		int lastPoints = Integer.parseInt(txt );
		String pointsText = " " + (lastPoints + this.points);
		points.setText(pointsText);
		this.points = 0;
	}

	private void UpdatePoints() {
		requestScores.updateScores(this, this.points);
	}

	@Override
	public void scoreReceivedSucceed(ScoreModel model) {
		turnOffProgressDialog();
		randomArray.clear();
		CreateRandomTest();
		FillRandomLetters(listcache);
		this.button.setEnabled(true);
	}

	@Override
	public void scoreReceivedFaild(String errorMessage) {
		Toast.makeText(getActivity(), errorMessage.toString(),
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void rankListReceivedSucceed(List<UserScoreModel> model) {
	}

	@Override
	public void rankListReceivedFaild(String errorMessage) {
		Toast.makeText(getActivity(), errorMessage.toString(),
				Toast.LENGTH_LONG).show();
	}

	private void turnOnProgressDialog(String title, String message) {
		this.dialog = ProgressDialog.show(getActivity(), title, message);
	}

	private void turnOffProgressDialog() {
		this.dialog.cancel();
	}

	@Override
	public void scoreUserReceivedSucceed(UserScoreModel model) {
	}

	@Override
	public void scoreUserReceivedFaild(String errorMessage) {
		Toast.makeText(getActivity(), errorMessage.toString(),
				Toast.LENGTH_LONG).show();
	}
}
