package com.example.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jassy on 2017/2/28.
 * description:
 */
public class ViewObject {
    private Map<String,Object> objs=new HashMap<>();
    public void set(String key,Object value){
        objs.put(key, value);
    }
    public Object get(String key){
        return objs.get(key);
    }
}
