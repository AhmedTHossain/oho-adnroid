package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("budget")
    @Expose
    private String budget;
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
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("prompt_answers")
    @Expose
    private List<PromptAnswer> promptAnswers = null;
    @SerializedName("race")
    @Expose
    private String race;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("vaccinated")
    @Expose
    private String vaccinated;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<PromptAnswer> getPromptAnswers() {
        return promptAnswers;
    }

    public void setPromptAnswers(List<PromptAnswer> promptAnswers) {
        this.promptAnswers = promptAnswers;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(String vaccinated) {
        this.vaccinated = vaccinated;
    }
}
