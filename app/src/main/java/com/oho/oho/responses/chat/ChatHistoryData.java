package com.oho.oho.responses.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oho.oho.responses.Chat;

import java.util.List;

public class ChatHistoryData {
    @SerializedName("data")
    @Expose
    private List<Chat> data;
    @SerializedName("has_next")
    @Expose
    private Boolean hasNext;
    @SerializedName("has_prev")
    @Expose
    private Boolean hasPrev;

    public List<Chat> getData() {
        return data;
    }

    public void setData(List<Chat> data) {
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
