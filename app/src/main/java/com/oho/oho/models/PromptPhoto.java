package com.oho.oho.models;

import java.io.File;

public class PromptPhoto {
    private File file;
    private String caption;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
