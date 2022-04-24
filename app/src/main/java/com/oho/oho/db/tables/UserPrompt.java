package com.oho.oho.db.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserPrompt {

    private String answer;

    private Integer userId;

    private Integer promptId;

    private Integer pictureId;

    @PrimaryKey
    private Integer orderNo;

    private String promptQuestion;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPromptId() {
        return promptId;
    }

    public void setPromptId(Integer promptId) {
        this.promptId = promptId;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getPromptQuestion() {
        return promptQuestion;
    }

    public void setPromptQuestion(String promptQuestion) {
        this.promptQuestion = promptQuestion;
    }
}
