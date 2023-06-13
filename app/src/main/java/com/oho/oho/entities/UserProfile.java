package com.oho.oho.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.oho.oho.models.PromptAnswer;

import java.util.List;

@Entity(tableName = "userprofile")
public class UserProfile {
    @PrimaryKey(autoGenerate = true)
    public int user_profile_id;
    @ColumnInfo(name="age")
    public int age;
    @ColumnInfo(name="bio")
    public String bio;
    @ColumnInfo(name="city")
    public String city;
    @ColumnInfo(name="dob")
    public String dob;
    @ColumnInfo(name="education")
    public String education;
    @ColumnInfo(name="email")
    public String email;
    @ColumnInfo(name="height")
    public double height;
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="lat")
    public double lat;
    @ColumnInfo(name="lon")
    public double lon;
    @ColumnInfo(name="name")
    public String name;
    @ColumnInfo(name="occupation")
    public String occupation;
    @ColumnInfo(name="phone")
    public String phone;
    @ColumnInfo(name="profile_picture")
    public String profilePicture;
    @ColumnInfo(name="prompt_answers")
    public List<PromptAnswer> promptAnswers;
    @ColumnInfo(name="race")
    public String race;
    @ColumnInfo(name="religion")
    public String religion;
    @ColumnInfo(name="sex")
    public String sex;
    @ColumnInfo(name="state")
    public String state;

    public UserProfile(int age, String bio, String city, String dob, String education, String email, double height, int id, double lat, double lon, String name, String occupation, String phone, String profilePicture, List<PromptAnswer> promptAnswers, String race, String religion, String sex, String state){
        this.age = age;
        this.bio = bio;
        this.city = city;
        this.dob = dob;
        this.education = education;
        this.email = email;
        this.height =height;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.occupation = occupation;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.promptAnswers = promptAnswers;
        this.race = race;
        this.religion = religion;
        this.sex = sex;
        this.state = state;
    }
}
