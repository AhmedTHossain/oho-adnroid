package com.oho.oho.responses.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetChatHistoryResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ChatHistoryData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ChatHistoryData getData() {
        return data;
    }

    public void setData(ChatHistoryData data) {
        this.data = data;
    }
}
