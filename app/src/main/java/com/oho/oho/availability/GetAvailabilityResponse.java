package com.oho.oho.availability;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oho.oho.models.Availability;

public class GetAvailabilityResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Availability data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Availability getData() {
        return data;
    }

    public void setData(Availability data) {
        this.data = data;
    }

}
