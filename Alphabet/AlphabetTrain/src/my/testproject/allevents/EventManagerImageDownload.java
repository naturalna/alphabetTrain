package my.testproject.allevents;

import java.util.ArrayList;
import java.util.List;

import my.startproject.datalayer.Item;


public class EventManagerImageDownload {
	private List<IdownloadedImage> listeners = new ArrayList<IdownloadedImage>();

    public void addListener(IdownloadedImage listener) {
        this.listeners.add(listener);
    }

    public void saySucceed(Item item) {
        // Notify everybody that may be interested.
        for (IdownloadedImage listener : listeners)
        	listener.Succeed(item);
    }
    
    public void Faild() {
        // Notify everybody that may be interested.
        for (IdownloadedImage listener : listeners)
        	listener.Faild();
    }
}
