package com.oho.oho.responses.jwttoken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JwtTokenData {
    @SerializedName("jwt_token")
    @Expose
    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
