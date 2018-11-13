package com.cloud.provider.dis;


import java.util.EventObject;
import java.util.Vector;

/**
 * @ClassName EventSource
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/12 17:29
 */
public class EventSource {

    private Vector<EventListener> listeners = new Vector<>();

    public void addListener(EventListener listener){
        listeners.add(listener);
    }

    public void removeListener(EventListener listener){
        listeners.remove(listener);
    }

    public void notifyListenerEvents(EventObject event){
        for(EventListener eventListener:listeners){
            eventListener.handleEvent(event);
        }
    }
}
