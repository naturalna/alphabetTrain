package my.startproject.alphabettrain;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HardFragment extends Fragment{
	
	public TextRecognition textRecognition;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 super.onCreateView(inflater,container,savedInstanceState);
		 	View view = inflater.inflate(R.layout.hard_fragment,
	        container, false);
		 	
	    return view;
	  }
	 
	 @Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		 super.onActivityCreated(savedInstanceState);
		 textRecognition = new TextRecognition(this.getActivity().getBaseContext(), this);
		// this.textRecognition = new TextRecognition(this.getActivity().getBaseContext(), this);
		 
		 Button button = (Button) getActivity().findViewById(R.id.buttonTest);

		 button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					textRecognition.getStartedTest();
				}
			});
	}
}
