package my.testproject.allevents;

import java.util.ArrayList;
import java.util.List;

public class EventManagerSignup {
	private List<ISignupListener> listeners = new ArrayList<ISignupListener>();

    public void addListener(ISignupListener listener) {
        this.listeners.add(listener);
    }

    public void saySucceed() {
        // Notify everybody that may be interested.
        for (ISignupListener listener : listeners)
        	listener.signupSucceed();
    }
    
    public void sayFaild() {
        // Notify everybody that may be interested.
        for (ISignupListener listener : listeners)
        	listener.signupFaild();
    }
}
