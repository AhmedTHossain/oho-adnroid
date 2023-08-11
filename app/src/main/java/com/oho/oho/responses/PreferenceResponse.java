package com.oho.oho.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreferenceResponse {
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("cuisine")
    @Expose
    private List<String> cuisine;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("interested_in")
    @Expose
    private String interestedIn;
    @SerializedName("max_age")
    @Expose
    private Integer maxAge;
    @SerializedName("max_height")
    @Expose
    private Integer maxHeight;
    @SerializedName("min_age")
    @Expose
    private Integer minAge;
    @SerializedName("min_height")
    @Expose
    private Integer minHeight;
    @SerializedName("race")
    @Expose
    private String race;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public List<String> getCuisine() {
        return cuisine;
    }

    public void setCuisine(List<String> cuisine) {
        this.cuisine = cuisine;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
