package com.oho.oho.models;

public class SelectedPrompt {
    private String prompt;
    private boolean isSelected;

    public SelectedPrompt(String prompt, boolean isSelected) {
        this.prompt = prompt;
        this.isSelected = isSelected;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }
}
