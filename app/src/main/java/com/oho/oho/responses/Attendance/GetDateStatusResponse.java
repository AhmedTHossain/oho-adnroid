package com.oho.oho.responses.Attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDateStatusResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private DateStatusData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public DateStatusData getData() {
        return data;
    }

    public void setData(DateStatusData data) {
        this.data = data;
    }
}
