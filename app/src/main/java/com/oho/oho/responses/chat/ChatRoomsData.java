package com.oho.oho.responses.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRoomsData {
    @SerializedName("data")
    @Expose
    private List<ChatRoom> data;
    @SerializedName("has_next")
    @Expose
    private Boolean hasNext;
    @SerializedName("has_prev")
    @Expose
    private Boolean hasPrev;

    public List<ChatRoom> getData() {
        return data;
    }

    public void setData(List<ChatRoom> data) {
        this.data = data;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean getHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(Boolean hasPrev) {
        this.hasPrev = hasPrev;
    }
}
