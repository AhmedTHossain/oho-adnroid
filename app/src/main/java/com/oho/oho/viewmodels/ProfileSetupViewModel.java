package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.oho.oho.models.NewPromptAnswer;
import com.oho.oho.repositories.ProfileSetupRepository;

import java.io.File;

public class ProfileSetupViewModel extends AndroidViewModel {
    public LiveData<Boolean> ifUploaded;
    public LiveData<Boolean> ifProfilePhotoUploaded;
    public LiveData<Boolean> ifGenderPreferenceUpdated;
    private ProfileSetupRepository repository;

    public ProfileSetupViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileSetupRepository(getApplication().getApplicationContext());
    }

    public void uploadPromptAnswer(NewPromptAnswer newPromptAnswer) {
        ifUploaded = repository.uploadNewPromptAnswer(newPromptAnswer, getApplication().getApplicationContext());
    }

    public void uploadProfilePhoto(int user_id, File image) {
        ifProfilePhotoUploaded = repository.uploadProfilePhoto(user_id, image, getApplication().getApplicationContext());
    }

    public void updateGenderPreference(int user_id, String interested_in){
        ifGenderPreferenceUpdated = repository.updateGenderPreference(user_id,interested_in,getApplication().getApplicationContext());
    }
}
