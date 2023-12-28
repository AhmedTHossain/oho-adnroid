package com.oho.oho.responses.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDatesLeftResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private DatesLeftData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public DatesLeftData getData() {
        return data;
    }

    public void setData(DatesLeftData data) {
        this.data = data;
    }
}
