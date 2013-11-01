package my.testproject.allevents;

import java.util.ArrayList;
import java.util.List;

import my.startproject.models.ScoreModel;
import my.startproject.models.UserScoreModel;

public class EventManagerScoreReceived {
	private List<IScoreReceived> listeners = new ArrayList<IScoreReceived>();

    public void addListener(IScoreReceived listener) {
        this.listeners.add(listener);
    }

    public void saySucceed(ScoreModel model) {
        // Notify everybody that may be interested.
        for (IScoreReceived listener : listeners)
        	listener.scoreReceivedSucceed(model);
    }
    
    public void Clear() {
        this.listeners.clear();
    }
    
    public void sayFaild(String errorMeggase) {
        // Notify everybody that may be interested.
        for (IScoreReceived listener : listeners)
        	listener.scoreReceivedFaild(errorMeggase);
    }
    
    public void sayRecived(List<UserScoreModel> model) {
        // Notify everybody that may be interested.
        for (IScoreReceived listener : listeners)
        	listener.rankListReceivedSucceed(model);
    }
    
    public void sayRecivedFaild(String errorMeggase) {
        // Notify everybody that may be interested.
        for (IScoreReceived listener : listeners)
        	listener.rankListReceivedFaild(errorMeggase);
    }
}
