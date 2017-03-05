package com.example.async;

import java.util.List;

/**
 * Created by Jassy on 2017/3/2.
 * description:
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportedEventTypes();
}
