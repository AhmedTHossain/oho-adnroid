package com.oho.oho.viewmodels;

import android.app.Application;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Cuisine;
import com.oho.oho.models.Profile;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.models.UserProfile;
import com.oho.oho.repositories.RegistrationRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class RegistrationViewModel extends AndroidViewModel {

    private RegistrationRepository registrationRepository;
    public LiveData<Profile> registeredUserProfileData;
    private ArrayList<String> preferredCuisineList;
    private double lat, lon;
    private String city, country;
    private int birthYear;


    private Profile userProfile;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        registrationRepository = new RegistrationRepository();
        preferredCuisineList = new ArrayList<>();
        userProfile = new Profile();
    }

    public void registerUser(Profile profile){
        registeredUserProfileData = registrationRepository.registerNewUser(profile, getApplication().getApplicationContext());
    }

    public void removeCuisine(String cuisine){
        preferredCuisineList.remove(cuisine);
    }

    public void addCuisine(String cuisine){
        preferredCuisineList.add(cuisine);
    }

    public String getPreferredCuisineList() {
        String cuisineList = "";
        for (int i=0; i<preferredCuisineList.size(); i++){
            if (i != preferredCuisineList.size()-1)
                cuisineList = cuisineList + preferredCuisineList.get(i) + ", ";
            else
                cuisineList = cuisineList + preferredCuisineList.get(i);
        }

        return cuisineList;
    }

    public void storeCoordinates(double lat, double lon, String city, String state){
        userProfile.setLat(lat);
        userProfile.setLon(lon);
        userProfile.setCity(city);
        userProfile.setState(state);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void calculateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        userProfile.setAge(currentYear - birthYear);
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void calculateHeight(int feet, int inch){
        int totalInch = (feet*12) + inch;
        double heightInCm = totalInch * 2.54;
        userProfile.setHeight(heightInCm);
    }

    public Profile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Profile userProfile) {
        this.userProfile = userProfile;
    }

    public void setCuisine(){
        String cuisine = "";
        for (String cuisineStr: preferredCuisineList){
            if (cuisine.equals(""))
                cuisine = cuisine + cuisineStr;
            else
                cuisine = cuisine + ", " + cuisineStr;
        }

//        userProfile.setCuisine(cuisine);
    }
}
