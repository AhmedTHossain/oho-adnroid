package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatesLeft {
    @SerializedName("dates_left")
    @Expose
    private Integer datesLeft;

    public Integer getDatesLeft() {
        return datesLeft;
    }

    public void setDatesLeft(Integer datesLeft) {
        this.datesLeft = datesLeft;
    }
}
