package my.testproject.allevents;

import java.util.ArrayList;
import java.util.List;

public class EventManagerSignin {
	private List<ISigninListener> listeners = new ArrayList<ISigninListener>();

	public void addListener(ISigninListener listener) {
		this.listeners.add(listener);
	}

	public void saySucceed() {
		// Notify everybody that may be interested.
		for (ISigninListener listener : listeners)
			listener.signinSucceed();
	}

	public void sayFaild() {
		// Notify everybody that may be interested.
		for (ISigninListener listener : listeners)
			listener.signinFaild();
	}
}
