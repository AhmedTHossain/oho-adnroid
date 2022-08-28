package com.oho.oho.models;

public class ProfileDisplay {
    private String imageUrl, name, age, location, profession, gender, height, race, religion, vaccinated, about, distance ;

    public ProfileDisplay(String imageUrl, String name, String age, String location, String profession, String gender, String height, String race, String religion, String vaccinated, String about, String distance) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.profession = profession;
        this.gender = gender;
        this.height = height;
        this.race = race;
        this.religion = religion;
        this.vaccinated = vaccinated;
        this.about = about;
        this.distance = distance;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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

    public String getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(String vaccinated) {
        this.vaccinated = vaccinated;
    }

    public String getDistance() {
        return distance;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
