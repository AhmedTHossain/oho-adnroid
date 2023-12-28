package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.models.User;
import com.oho.oho.repositories.LikeYouRepository;

import java.util.List;

public class LikeYouVIewModel extends AndroidViewModel {

    private LikeYouRepository likeYouRepository;
    public LiveData<List<Profile>> userList;
    public LiveData<Boolean> isDateAvailable;

    public LikeYouVIewModel(@NonNull Application application) {
        super(application);
        likeYouRepository = new LikeYouRepository(getApplication().getApplicationContext());
    }

    public void getAllLikedByProfiles(String jwtToken) {
        userList = likeYouRepository.getLikedByProfiles(jwtToken);
    }

    public void getNumberOfDates(){
        isDateAvailable = likeYouRepository.getNumberOfDatesAvailable();
    }
}
