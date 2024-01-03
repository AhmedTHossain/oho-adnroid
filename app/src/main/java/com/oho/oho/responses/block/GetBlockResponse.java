package com.oho.oho.responses.block;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBlockResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private BlockData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BlockData getData() {
        return data;
    }

    public void setData(BlockData data) {
        this.data = data;
    }
}
