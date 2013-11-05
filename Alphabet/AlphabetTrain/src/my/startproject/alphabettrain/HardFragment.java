package my.startproject.alphabettrain;

import java.math.BigInteger;
import java.security.SecureRandom;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HardFragment extends Fragment{
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
		 setText(); 
	}

	  public void setText() {
		  TextView v = (TextView) getView().findViewById(R.id.test);
		   // SessionIdentifierGenerator gen = new SessionIdentifierGenerator();
		  String text = "Harddddddd"; ///gen.nextSessionId();
		  v.setText(text);
	  }
	  
	  public final class SessionIdentifierGenerator
	  {
	    private SecureRandom random = new SecureRandom();

	    public String nextSessionId()
	    {
	      return new BigInteger(130, random).toString(32);
	    }
	  }
}
