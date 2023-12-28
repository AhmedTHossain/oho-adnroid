package com.oho.oho.responses.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostReportUserResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ReportUserData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ReportUserData getData() {
        return data;
    }

    public void setData(ReportUserData data) {
        this.data = data;
    }
}
