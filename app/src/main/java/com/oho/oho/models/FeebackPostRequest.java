package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeebackPostRequest {
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("date_rating")
    @Expose
    private String dateRating;
    @SerializedName("restaurant_rating")
    @Expose
    private String restaurantRating;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getDateRating() {
        return dateRating;
    }

    public void setDateRating(String dateRating) {
        this.dateRating = dateRating;
    }

    public String getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(String restaurantRating) {
        this.restaurantRating = restaurantRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
