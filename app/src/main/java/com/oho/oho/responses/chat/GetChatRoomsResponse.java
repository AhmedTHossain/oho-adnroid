package com.oho.oho.responses.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetChatRoomsResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ChatRoomsData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ChatRoomsData getData() {
        return data;
    }

    public void setData(ChatRoomsData data) {
        this.data = data;
    }
}
