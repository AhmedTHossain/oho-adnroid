package com.oho.oho.responses.preference;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPreferenceResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private PreferenceResponse data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PreferenceResponse getData() {
        return data;
    }

    public void setData(PreferenceResponse data) {
        this.data = data;
    }
}
