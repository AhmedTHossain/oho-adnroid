package com.oho.oho.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromptAnswer {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("prompt")
    @Expose
    private String prompt;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("caption")
    @Expose
    private String caption;

    public PromptAnswer() {
    }

    public PromptAnswer(Integer id, String prompt, String answer, Integer userId, String image, String caption) {
        this.id = id;
        this.prompt = prompt;
        this.answer = answer;
        this.userId = userId;
        this.image = image;
        this.caption = caption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
