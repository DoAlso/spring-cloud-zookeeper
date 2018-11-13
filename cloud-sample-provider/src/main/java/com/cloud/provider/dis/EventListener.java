package com.cloud.provider.dis;

import java.util.EventObject;

public interface EventListener extends java.util.EventListener {

    void handleEvent(EventObject object);

}
