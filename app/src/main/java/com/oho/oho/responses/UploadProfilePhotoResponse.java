package com.oho.oho.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadProfilePhotoResponse {
    @SerializedName("caption")
    @Expose
    private Object caption;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("profile_picture_id")
    @Expose
    private Integer profilePictureId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public Object getCaption() {
        return caption;
    }

    public void setCaption(Object caption) {
        this.caption = caption;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(Integer profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
