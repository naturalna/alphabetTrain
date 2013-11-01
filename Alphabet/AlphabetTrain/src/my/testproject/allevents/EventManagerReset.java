package my.testproject.allevents;

import java.util.ArrayList;
import java.util.List;

public class EventManagerReset {
	private List<IPasswordReset> listeners = new ArrayList<IPasswordReset>();

    public void addListener(IPasswordReset listener) {
        this.listeners.add(listener);
    }

    public void saySucceed() {
        // Notify everybody that may be interested.
        for (IPasswordReset listener : listeners)
        	listener.resetSucceed();
    }
    
    public void sayFaild() {
        // Notify everybody that may be interested.
        for (IPasswordReset listener : listeners)
        	listener.resetFaild();
    }
}
