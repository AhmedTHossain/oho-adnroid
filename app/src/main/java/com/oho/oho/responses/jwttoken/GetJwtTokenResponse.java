package com.oho.oho.responses.jwttoken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetJwtTokenResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private JwtTokenData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public JwtTokenData getData() {
        return data;
    }

    public void setData(JwtTokenData data) {
        this.data = data;
    }
}
