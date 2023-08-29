package com.oho.oho.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreferenceResponse {
    @SerializedName("budget")
    @Expose
    private List<String> budget;
    @SerializedName("cuisine")
    @Expose
    private List<String> cuisine;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("education")
    @Expose
    private List<String> education;
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
    private List<String> race;
    @SerializedName("religion")
    @Expose
    private List<String> religion;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public List<String> getBudget() {
        return budget;
    }

    public void setBudget(List<String> budget) {
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

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
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

    public List<String> getRace() {
        return race;
    }

    public void setRace(List<String> race) {
        this.race = race;
    }

    public List<String> getReligion() {
        return religion;
    }

    public void setReligion(List<String> religion) {
        this.religion = religion;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
