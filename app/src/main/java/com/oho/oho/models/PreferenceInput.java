package com.oho.oho.models;

public class PreferenceInput {
    private String inputName;
    private boolean isSelected;

    public PreferenceInput(String inputName, boolean isSelected) {
        this.inputName = inputName;
        this.isSelected = isSelected;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
