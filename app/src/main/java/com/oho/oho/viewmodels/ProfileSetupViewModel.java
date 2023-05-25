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
    private ProfileSetupRepository repository;

    public ProfileSetupViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileSetupRepository(getApplication().getApplicationContext());
    }

    public void uploadPromptAnswer(NewPromptAnswer newPromptAnswer){
        ifUploaded = repository.uploadNewPromptAnswer(newPromptAnswer,getApplication().getApplicationContext());
    }
}
