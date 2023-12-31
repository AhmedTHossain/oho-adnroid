package com.oho.oho.responses.upcomingdates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oho.oho.models.UpcomingDate;

import java.util.List;

public class GetUpcomingDatesResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<UpcomingDate> data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<UpcomingDate> getData() {
        return data;
    }

    public void setData(List<UpcomingDate> data) {
        this.data = data;
    }
}
