package com.example.Model;

import java.util.Date;

/**
 * Created by Jassy on 2017/2/24.
 * description: 作为cookie,记录用户的登录状态;
 */
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private boolean isValidStatus;
    private String ticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public boolean isValidStatus() {
        return isValidStatus;
    }

    public void setValidStatus(boolean validStatus) {
        isValidStatus = validStatus;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
