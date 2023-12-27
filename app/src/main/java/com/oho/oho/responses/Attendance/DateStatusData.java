package com.oho.oho.responses.Attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateStatusData {
    @SerializedName("attendance")
    @Expose
    private Boolean attendance;

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }
}
