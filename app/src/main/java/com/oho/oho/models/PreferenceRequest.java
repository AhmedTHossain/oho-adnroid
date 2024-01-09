package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreferenceRequest {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("interested_in")
    @Expose
    private String interestedIn;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("race")
    @Expose
    private String race;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("cuisine")
    @Expose
    private String cuisine;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("max_height")
    @Expose
    private Double maxHeight;
    @SerializedName("min_height")
    @Expose
    private Double minHeight;
    @SerializedName("max_age")
    @Expose
    private Integer maxAge;
    @SerializedName("min_age")
    @Expose
    private Integer minAge;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Double minHeight) {
        this.minHeight = minHeight;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }
}
