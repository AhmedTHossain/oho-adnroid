package com.oho.oho.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadPromptPhotoResponse {
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("picture_id")
    @Expose
    private Integer pictureId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
