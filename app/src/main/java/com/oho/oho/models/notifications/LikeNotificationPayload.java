package com.oho.oho.models.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LikeNotificationPayload implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("senderName")
    @Expose
    private String senderName;
    @SerializedName("senderPhoto")
    @Expose
    private String senderPhoto;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("type")
    @Expose
    private String type;

    public LikeNotificationPayload(String title, String body, String senderName, String senderPhoto, String userId, String type) {
        this.title = title;
        this.body = body;
        this.senderName = senderName;
        this.senderPhoto = senderPhoto;
        this.userId = userId;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
