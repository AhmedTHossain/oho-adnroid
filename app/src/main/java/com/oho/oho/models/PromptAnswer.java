package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromptAnswer {
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("prompt_id")
    @Expose
    private Integer promptId;
    @SerializedName("picture_id")
    @Expose
    private Integer pictureId;
    @SerializedName("order_no")
    @Expose
    private Integer orderNo;

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
}
