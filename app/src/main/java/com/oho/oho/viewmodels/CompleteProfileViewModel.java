package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.oho.oho.models.Profile;
import com.oho.oho.repositories.CompleteProfileRepository;

public class CompleteProfileViewModel extends AndroidViewModel {

    private CompleteProfileRepository completeProfileRepository;

    public CompleteProfileViewModel(@NonNull Application application) {
        super(application);
        completeProfileRepository = new CompleteProfileRepository();
    }

    public void updateUser(Profile profile){
        completeProfileRepository.updateUserProfile(profile, getApplication().getApplicationContext());
    }
}
