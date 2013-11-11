package my.testproject.allevents;

import java.util.ArrayList;
import java.util.List;

public class EventManegerLetterRecived {
	private List<ILetterRecived> listeners = new ArrayList<ILetterRecived>();

    public void addListener(ILetterRecived listener) {
        this.listeners.add(listener);
    }

    public void saySucceed() {
        // Notify everybody that may be interested.
        for (ILetterRecived listener : listeners)
        	listener.SucceedLettersRecived();
    }
    
    public void Faild() {
        // Notify everybody that may be interested.
        for (ILetterRecived listener : listeners)
        	listener.FaildLettersRecived();
    }
    
    public void Clear(){
    	listeners.clear();
    }
}
