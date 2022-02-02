package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("active_hour")
    @Expose
    private String activeHour;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("budget_range")
    @Expose
    private String budgetRange;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("height_ft_max")
    @Expose
    private Integer heightFtMax;
    @SerializedName("height_ft_min")
    @Expose
    private Integer heightFtMin;
    @SerializedName("height_inch_max")
    @Expose
    private Integer heightInchMax;
    @SerializedName("height_inch_min")
    @Expose
    private Integer heightInchMin;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("phone")
    @Expose
    private Integer phone;
    @SerializedName("preferred_education")
    @Expose
    private String preferredEducation;
    @SerializedName("preferred_gender")
    @Expose
    private String preferredGender;
    @SerializedName("preferred_location")
    @Expose
    private String preferredLocation;
    @SerializedName("preferred_race")
    @Expose
    private String preferredRace;
    @SerializedName("preferred_religion")
    @Expose
    private String preferredReligion;
    @SerializedName("proximity_range")
    @Expose
    private String proximityRange;
    @SerializedName("state")
    @Expose
    private String state;

    public String getActiveHour() {
        return activeHour;
    }

    public void setActiveHour(String activeHour) {
        this.activeHour = activeHour;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBudgetRange() {
        return budgetRange;
    }

    public void setBudgetRange(String budgetRange) {
        this.budgetRange = budgetRange;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getHeightFtMax() {
        return heightFtMax;
    }

    public void setHeightFtMax(Integer heightFtMax) {
        this.heightFtMax = heightFtMax;
    }

    public Integer getHeightFtMin() {
        return heightFtMin;
    }

    public void setHeightFtMin(Integer heightFtMin) {
        this.heightFtMin = heightFtMin;
    }

    public Integer getHeightInchMax() {
        return heightInchMax;
    }

    public void setHeightInchMax(Integer heightInchMax) {
        this.heightInchMax = heightInchMax;
    }

    public Integer getHeightInchMin() {
        return heightInchMin;
    }

    public void setHeightInchMin(Integer heightInchMin) {
        this.heightInchMin = heightInchMin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getPreferredEducation() {
        return preferredEducation;
    }

    public void setPreferredEducation(String preferredEducation) {
        this.preferredEducation = preferredEducation;
    }

    public String getPreferredGender() {
        return preferredGender;
    }

    public void setPreferredGender(String preferredGender) {
        this.preferredGender = preferredGender;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getPreferredRace() {
        return preferredRace;
    }

    public void setPreferredRace(String preferredRace) {
        this.preferredRace = preferredRace;
    }

    public String getPreferredReligion() {
        return preferredReligion;
    }

    public void setPreferredReligion(String preferredReligion) {
        this.preferredReligion = preferredReligion;
    }

    public String getProximityRange() {
        return proximityRange;
    }

    public void setProximityRange(String proximityRange) {
        this.proximityRange = proximityRange;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
