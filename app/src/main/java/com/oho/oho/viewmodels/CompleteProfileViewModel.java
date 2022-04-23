package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.CompleteProfileInput;
import com.oho.oho.models.Profile;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.repositories.CompleteProfileRepository;
import com.oho.oho.repositories.PromptRepository;

import java.io.File;

public class CompleteProfileViewModel extends AndroidViewModel {

    private CompleteProfileRepository completeProfileRepository;
    public LiveData<Integer> uploadedPhotoId;

    private PromptRepository promptRepository;

    //this object is going to store user's inputs during the complete profile process
    private MutableLiveData<CompleteProfileInput> completeProfileInputData = new MutableLiveData<>();

    public CompleteProfileViewModel(@NonNull Application application) {
        super(application);
        completeProfileRepository = new CompleteProfileRepository();
        promptRepository = new PromptRepository();
    }

    public void updateUser(Profile profile){
        completeProfileRepository.updateUserProfile(profile, getApplication().getApplicationContext());
    }

    public void uploadProfilePhoto(int id, File file){
        completeProfileRepository.updateUserProfilePhoto(id,file,getApplication().getApplicationContext());
    }

    public void uploadPromptPhoto(int id, String captionText, File file){
        uploadedPhotoId = completeProfileRepository.uploadUserPromptPhoto(id,captionText,file,getApplication().getApplicationContext());
    }

    public void saveCompletProfileInputData(CompleteProfileInput profile) {
        completeProfileInputData.setValue(profile);
    }

    public LiveData<CompleteProfileInput> getCompletProfileInputData() {
        return completeProfileInputData;
    }

    public void uploadUserPrompt(PromptAnswer promptAnswer){
        promptRepository.addPrompt(promptAnswer, getApplication().getApplicationContext());
    }
}
