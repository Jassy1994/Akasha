package com.example.Model;

import java.util.Date;

/**
 * entityType:0-User;1-Information;2-Question;3-Answer;4-comment;
 * Created by Jassy on 2017/2/21.
 */
public class Comment {
    private int id;
    private int userId;
    private int entityType;
    private int entityId;
    private String content;
    private int agreementNum;
    private Date commentDate;
    private int status;

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

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAgreementNum() {
        return agreementNum;
    }

    public void setAgreementNum(int agreementNum) {
        this.agreementNum = agreementNum;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
