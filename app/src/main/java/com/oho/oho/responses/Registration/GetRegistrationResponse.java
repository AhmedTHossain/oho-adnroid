package com.oho.oho.responses.Registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oho.oho.models.Profile;

public class GetRegistrationResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Profile data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Profile getData() {
        return data;
    }

    public void setData(Profile data) {
        this.data = data;
    }
}
