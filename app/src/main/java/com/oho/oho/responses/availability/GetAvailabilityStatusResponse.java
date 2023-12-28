package com.oho.oho.responses.availability;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAvailabilityStatusResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private AvailabilityStatusData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public AvailabilityStatusData getData() {
        return data;
    }

    public void setData(AvailabilityStatusData data) {
        this.data = data;
    }
}
