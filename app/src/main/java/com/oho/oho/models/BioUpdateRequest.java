package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BioUpdateRequest {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("bio")
    @Expose
    private String bio;
    public BioUpdateRequest(Integer id, String bio) {
        this.id = id;
        this.bio = bio;
    }

}
