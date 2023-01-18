package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.models.Swipe;
import com.oho.oho.repositories.SwipeRepository;

public class LikedByViewModel extends AndroidViewModel {
    private SwipeRepository swipeRepository;
    public LiveData<Boolean> isSwipeSuccessful;
    public LikedByViewModel(@NonNull Application application) {
        super(application);
        swipeRepository = new SwipeRepository(getApplication().getApplicationContext());
    }
    public void swipeUserProfile(Swipe swipeProfile){
        isSwipeSuccessful = swipeRepository.swipeRecommendedProfile(swipeProfile);
    }
}
