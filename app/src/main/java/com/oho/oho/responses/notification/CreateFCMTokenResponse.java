package com.oho.oho.responses.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oho.oho.responses.preference.PreferenceResponse;

public class CreateFCMTokenResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private StoreDeviceIdResponse data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public StoreDeviceIdResponse getData() {
        return data;
    }

    public void setData(StoreDeviceIdResponse data) {
        this.data = data;
    }
}
