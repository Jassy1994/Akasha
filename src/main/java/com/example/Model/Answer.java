package com.example.Model;

import java.util.Date;

/**
 * Created by Jassy on 2017/2/21.
 */
public class Answer {
    private int id;
    private int userId;
    private int questionId;
    private boolean isAnonymous;
    private String content;
    private int agreementNum;
    private int commentNum;
    private Date answerDate;

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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
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

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }
}
