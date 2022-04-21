package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prompt {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("prompt_id")
    @Expose
    private Integer promptId;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPromptId() {
        return promptId;
    }

    public void setPromptId(Integer promptId) {
        this.promptId = promptId;
    }
}
