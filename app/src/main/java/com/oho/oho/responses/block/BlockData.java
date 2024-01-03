package com.oho.oho.responses.block;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("channel_name")
    @Expose
    private String channelName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("participants")
    @Expose
    private String participants;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("blocked_by")
    @Expose
    private Object blockedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Object getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(Object blockedBy) {
        this.blockedBy = blockedBy;
    }
}
