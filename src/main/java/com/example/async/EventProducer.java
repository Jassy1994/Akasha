package com.example.async;

import com.example.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jassy on 2017/3/1.
 * description:
 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean releaseEvent(EventModel eventModel){

        return false;
    }
}
