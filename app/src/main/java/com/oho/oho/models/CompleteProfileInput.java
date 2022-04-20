package com.oho.oho.models;

import java.io.File;

public class CompleteProfileInput {
    private File profilePhoto;
    private String bio;
    private PromptPhoto firstPromptPhoto, secondPromptPhoto, thirdPromptPhoto;
    private PromptAnswer firstPrompt, secondPrompt, thirdPrompt;

    public File getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(File profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public PromptPhoto getFirstPromptPhoto() {
        return firstPromptPhoto;
    }

    public void setFirstPromptPhoto(PromptPhoto firstPromptPhoto) {
        this.firstPromptPhoto = firstPromptPhoto;
    }

    public PromptPhoto getSecondPromptPhoto() {
        return secondPromptPhoto;
    }

    public void setSecondPromptPhoto(PromptPhoto secondPromptPhoto) {
        this.secondPromptPhoto = secondPromptPhoto;
    }

    public PromptPhoto getThirdPromptPhoto() {
        return thirdPromptPhoto;
    }

    public void setThirdPromptPhoto(PromptPhoto thirdPromptPhoto) {
        this.thirdPromptPhoto = thirdPromptPhoto;
    }

    public PromptAnswer getFirstPrompt() {
        return firstPrompt;
    }

    public void setFirstPrompt(PromptAnswer firstPrompt) {
        this.firstPrompt = firstPrompt;
    }

    public PromptAnswer getSecondPrompt() {
        return secondPrompt;
    }

    public void setSecondPrompt(PromptAnswer secondPrompt) {
        this.secondPrompt = secondPrompt;
    }

    public PromptAnswer getThirdPrompt() {
        return thirdPrompt;
    }

    public void setThirdPrompt(PromptAnswer thirdPrompt) {
        this.thirdPrompt = thirdPrompt;
    }
}
