package com.oho.oho.responses.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportUserData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("reporter_id")
    @Expose
    private Integer reporterId;
    @SerializedName("reported_id")
    @Expose
    private Integer reportedId;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReporterId() {
        return reporterId;
    }

    public void setReporterId(Integer reporterId) {
        this.reporterId = reporterId;
    }

    public Integer getReportedId() {
        return reportedId;
    }

    public void setReportedId(Integer reportedId) {
        this.reportedId = reportedId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }
}
