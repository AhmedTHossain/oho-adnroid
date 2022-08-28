package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.oho.oho.models.Swipe;
import com.oho.oho.repositories.SwipeRepository;

public class HomeViewModel extends AndroidViewModel {

    private SwipeRepository swipeRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        swipeRepository = new SwipeRepository();
    }

    public void swipeUserProfile(Swipe swipeProfile){
        swipeRepository.swipeRecommendedProfile(swipeProfile);
    }
}
