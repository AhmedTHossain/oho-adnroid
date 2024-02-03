package com.oho.oho.models.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeedbackNotificationPayload implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("match_id")
    @Expose
    private Integer matchId;
    @SerializedName("sender_name")
    @Expose
    private String senderName;
    @SerializedName("sender_photo")
    @Expose
    private String senderPhoto;
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

    public FeedbackNotificationPayload(String title, String body, Integer matchId, String senderName,  String senderPhoto, String restaurantName, Integer reservationTime, Integer restaurantId, String type) {
        this.title = title;
        this.body = body;
        this.matchId = matchId;
        this.senderName = senderName;
        this.senderPhoto = senderPhoto;
        this.restaurantName = restaurantName;
        this.reservationTime = reservationTime;
        this.restaurantId = restaurantId;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
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
