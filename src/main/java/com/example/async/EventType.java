package com.example.async;

/**
 * Created by Jassy on 2017/3/2.
 * description:
 */
public enum EventType {
    LOGIN(0),
    AGREEMENT(1),
    COMMENT(2),
    MESSAGE(3),
    ADD_QUESTION(4),
    FOLLOW(5),
    UNFOLLOW(6);

    private int value;
    EventType(int value){this.value=value;}
    public int getValue(){
        return value;
    }


}
