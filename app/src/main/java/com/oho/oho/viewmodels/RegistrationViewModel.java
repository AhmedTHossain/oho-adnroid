package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.repositories.RegistrationRepository;

public class RegistrationViewModel extends AndroidViewModel {

    private RegistrationRepository registrationRepository;
    public LiveData<Profile> registeredUserProfileData;

    private final MutableLiveData<Profile> userProfileData = new MutableLiveData<>();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        registrationRepository = new RegistrationRepository();
    }

    public void registerUser(Profile profile){
        registeredUserProfileData = registrationRepository.registerNewUser(profile);
    }

    public void saveProfileData(Profile profile) {
        userProfileData.setValue(profile);
    }

    public LiveData<Profile> getProfileData() {
        return userProfileData;
    }
}
