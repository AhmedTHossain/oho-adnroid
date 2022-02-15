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

    //this object is going to store user's inputs during the registration process
    private MutableLiveData<Profile> registrationFormData = new MutableLiveData<>();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        registrationRepository = new RegistrationRepository();
    }

    public void registerUser(Profile profile){
        registeredUserProfileData = registrationRepository.registerNewUser(profile);
    }

    public void saveRegistrationFormData(Profile profile) {
        registrationFormData.setValue(profile);
    }

    public LiveData<Profile> getRegistrationFormData() {
        return registrationFormData;
    }
}
