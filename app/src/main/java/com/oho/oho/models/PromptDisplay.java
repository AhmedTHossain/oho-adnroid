package com.oho.oho.models;

public class PromptDisplay {

    private String prompt, answer, imageUrl, caption;

    public PromptDisplay(String prompt, String answer, String imageUrl, String caption) {
        this.prompt = prompt;
        this.answer = answer;
        this.imageUrl = imageUrl;
        this.caption = caption;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
