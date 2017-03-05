package com.example.async.handler;

import com.example.async.EventHandler;
import com.example.async.EventModel;
import com.example.async.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jassy on 2017/3/2.
 * description:
 */
@Component
public class AddInformationHandler implements EventHandler {

    private final static Logger logger= LoggerFactory.getLogger(AddInformationHandler.class);

    @Override
    public void doHandle(EventModel model) {

    }

    @Override
    public List<EventType> getSupportedEventTypes() {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
