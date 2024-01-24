package com.oho.oho.responses.swipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oho.oho.responses.SwipeMessageResponse;

public class PostSwipeResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private SwipeMessageResponse data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public SwipeMessageResponse getData() {
        return data;
    }

    public void setData(SwipeMessageResponse data) {
        this.data = data;
    }
}
