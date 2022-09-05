package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Cuisine;
import com.oho.oho.models.Profile;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.repositories.RegistrationRepository;

import java.util.ArrayList;

public class RegistrationViewModel extends AndroidViewModel {

    private RegistrationRepository registrationRepository;
    public LiveData<Profile> registeredUserProfileData;
    private ArrayList<String> preferredCuisineList;
    private double lat, lon;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        registrationRepository = new RegistrationRepository();
        preferredCuisineList = new ArrayList<>();
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

    public void storeCoordinates(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
