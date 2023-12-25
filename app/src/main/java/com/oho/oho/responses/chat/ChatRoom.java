package com.oho.oho.responses.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChatRoom implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("channel_name")
    @Expose
    private String channelName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("participants")
    @Expose
    private String participants;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("last_message")
    @Expose
    private String lastMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}
