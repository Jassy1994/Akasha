package com.example.Model;

import org.springframework.stereotype.Component;

/**
 * Created by Jassy on 2017/2/28.
 * description:每个会话的用户;
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users=new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }
    public void setUser(User user){
        users.set(user);
    }
    public void clear(){
        users.remove();
    }
}
