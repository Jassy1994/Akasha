package com.example.async.handler;

import com.example.async.EventHandler;
import com.example.async.EventModel;
import com.example.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jassy on 2017/3/2.
 * description:
 */
@Component
public class LoginExceptionHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {
        //用户异常登录;
        Map<String, Object> map = new HashMap<>();
        map.put("username", model.getExt("username"));

    }

    @Override
    public List<EventType> getSupportedEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
