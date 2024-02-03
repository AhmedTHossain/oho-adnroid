package com.oho.oho.models.notifications;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChatNotificationPayload  implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("sender_name")
    @Expose
    private String senderName;
    @SerializedName("sender_photo")
    @Expose
    private String senderPhoto;
    @SerializedName("chat_id")
    @Expose
    private String chatId;
    @SerializedName("channel_name")
    @Expose
    private String channelName;

    @SerializedName("type")
    @Expose
    private String type;

    public ChatNotificationPayload(String title, String body, String senderName, String senderPhoto, String chatId, String channelName, String type) {
        this.title = title;
        this.body = body;
        this.senderName = senderName;
        this.senderPhoto = senderPhoto;
        this.chatId = chatId;
        this.channelName = channelName;
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

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
//    @NonNull
//    @Override
//    public String toString() {
//        return "ChatNotificationPayload{" +
//                "title='" + title + '\'' +
//                ", body='" + body + '\'' +
//                ", senderName='" + senderName + '\'' +
//                ", senderPhoto='" + senderPhoto + '\'' +
//                ", chatId='" + chatId + '\'' +
//                ", channelName='" + channelName + '\'' +
//                '}';
//    }
}
