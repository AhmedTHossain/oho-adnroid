package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.models.Swipe;
import com.oho.oho.models.User;
import com.oho.oho.repositories.AvailabilitySettingsRepository;
import com.oho.oho.repositories.SwipeRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private SwipeRepository swipeRepository;
    public LiveData<List<Profile>> recommendedProfiles;
    public int profileToShow;
    public LiveData<Boolean> isSwipeSuccessful;
    public LiveData<Boolean> isAvailable;
    public LiveData<Integer> numberOfDatesLeft;
    private AvailabilitySettingsRepository availabilitySettingsRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        swipeRepository = new SwipeRepository(getApplication().getApplicationContext());
        availabilitySettingsRepository = new AvailabilitySettingsRepository();
    }

    public void getRecommendation(int user_id){
        recommendedProfiles = swipeRepository.getRecommendedProfiles(String.valueOf(user_id));

        profileToShow = 0;
    }

    public void swipeUserProfile(Swipe swipeProfile){
        isSwipeSuccessful = swipeRepository.swipeRecommendedProfile(swipeProfile);
    }

    public void setProfileToShow(int id){
        profileToShow = id;
    }

    public int getProfileToShow(){
        return profileToShow;
    }

    public void checkIfAvailable(int user_id){
        isAvailable = availabilitySettingsRepository.checkIfAvailable(getApplication().getApplicationContext());
    }

    public void getNumberOfDatesLeft(int user_id){
        numberOfDatesLeft = swipeRepository.getNumberOfDatesLeft(user_id);
    }
}
