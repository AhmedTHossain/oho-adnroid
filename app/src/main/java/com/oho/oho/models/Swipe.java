package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Swipe {
    @SerializedName("profile_shown")
    @Expose
    private Integer profileShown;
    @SerializedName("direction")
    @Expose
    private Integer direction;

    public Integer getProfileShown() {
        return profileShown;
    }

    public void setProfileShown(Integer profileShown) {
        this.profileShown = profileShown;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}
