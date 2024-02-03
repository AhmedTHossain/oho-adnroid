package com.oho.oho.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class NotificationPreference implements Parcelable {
    private Boolean likeNotification, chatNotification, matchNotification;

    public NotificationPreference(Boolean likeNotification, Boolean chatNotification, Boolean matchNotification) {
        this.likeNotification = likeNotification;
        this.chatNotification = chatNotification;
        this.matchNotification = matchNotification;
    }

    public Boolean getLikeNotification() {
        return likeNotification;
    }

    public void setLikeNotification(Boolean likeNotification) {
        this.likeNotification = likeNotification;
    }

    public Boolean getChatNotification() {
        return chatNotification;
    }

    public void setChatNotification(Boolean chatNotification) {
        this.chatNotification = chatNotification;
    }

    public Boolean getMatchNotification() {
        return matchNotification;
    }

    public void setMatchNotification(Boolean matchNotification) {
        this.matchNotification = matchNotification;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeBoolean(likeNotification);
        dest.writeBoolean(chatNotification);
        dest.writeBoolean(matchNotification);
    }

    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static NotificationPreference fromJsonString(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, NotificationPreference.class);
    }
}
