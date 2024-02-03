package com.oho.oho.models.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReminderNotificationPayload implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("user_a_name")
    @Expose
    private String userAName;
    @SerializedName("user_b_name")
    @Expose
    private String userBName;
    @SerializedName("user_a_pro_pic")
    @Expose
    private String userAProPic;
    @SerializedName("user_b_pro_pic")
    @Expose
    private String userBProPic;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("reservation_time")
    @Expose
    private Integer reservationTime;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("type")
    @Expose
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserAName() {
        return userAName;
    }

    public void setUserAName(String userAName) {
        this.userAName = userAName;
    }

    public String getUserBName() {
        return userBName;
    }

    public void setUserBName(String userBName) {
        this.userBName = userBName;
    }

    public String getUserAProPic() {
        return userAProPic;
    }

    public void setUserAProPic(String userAProPic) {
        this.userAProPic = userAProPic;
    }

    public String getUserBProPic() {
        return userBProPic;
    }

    public void setUserBProPic(String userBProPic) {
        this.userBProPic = userBProPic;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Integer reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
