package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.repositories.ProfileViewRepository;

public class ProfileViewModel extends AndroidViewModel {
    public LiveData<Profile> userProfile;
    private ProfileViewRepository repository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileViewRepository(getApplication().getApplicationContext());
    }

    public void getProfile(int userId) {
        userProfile = repository.getUserProfile(userId);
    }
}
