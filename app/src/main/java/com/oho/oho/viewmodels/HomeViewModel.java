package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.models.Swipe;
import com.oho.oho.models.User;
import com.oho.oho.repositories.SwipeRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private SwipeRepository swipeRepository;
    public LiveData<List<User>> recommendedProfiles;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        swipeRepository = new SwipeRepository(getApplication().getApplicationContext());
    }

    public void getRecommendation(int user_id){
        recommendedProfiles = swipeRepository.getRecommendedProfiles(String.valueOf(user_id));
    }

    public void swipeUserProfile(Swipe swipeProfile){
        swipeRepository.swipeRecommendedProfile(swipeProfile);
    }
}
