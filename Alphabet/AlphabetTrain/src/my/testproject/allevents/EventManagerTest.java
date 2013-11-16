package my.testproject.allevents;

import java.util.ArrayList;
import java.util.List;

import my.startproject.datalayer.Item;


public class EventManagerTest {
	private List<ITestRecived> listeners = new ArrayList<ITestRecived>();

    public void addListener(ITestRecived listener) {
        this.listeners.add(listener);
    }

    public void saySucceed(ArrayList<Item> item) {
        // Notify everybody that may be interested.
        for (ITestRecived listener : listeners)
        	listener.Succeed(item);
    }
    
    public void Faild() {
        // Notify everybody that may be interested.
        for (ITestRecived listener : listeners)
        	listener.Faild();
    }
    
    public void Clear(){
    	this.listeners.clear();
    }
}
