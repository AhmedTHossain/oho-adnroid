package com.oho.oho.responses.uploadPhoto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadProfilePhotoResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ProfilePhotoResponse data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ProfilePhotoResponse getData() {
        return data;
    }

    public void setData(ProfilePhotoResponse data) {
        this.data = data;
    }
}
