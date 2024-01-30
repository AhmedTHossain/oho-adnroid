package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.models.BioUpdateRequest;
import com.oho.oho.repositories.ProfileViewRepository;

import java.io.File;

public class ProfileViewModel extends AndroidViewModel {
    public LiveData<Profile> userProfile;
    public LiveData<Boolean> ifBioUpdated;
    public LiveData<Boolean> ifPromptDeleted;
    private ProfileViewRepository repository;
    private MutableLiveData<Integer> promptToDelete = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifEditBio = new MutableLiveData<>();

    private MutableLiveData<Boolean> ifEditPhoto = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifAddPrompt = new MutableLiveData<>();
    public LiveData<String> uploadedImagePath;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileViewRepository(getApplication().getApplicationContext());
    }

    public void getProfile(int userId) {
        userProfile = repository.getUserProfile(userId);
    }

    public void deletePrompt(int promptId) {
        promptToDelete.setValue(promptId);
    }
    public LiveData<Integer> getPromptToDelete() {
        return promptToDelete;
    }

    public void editBio() {
        ifEditBio.setValue(true);
    }
    public LiveData<Boolean> getIfEditBio() {
        return ifEditBio;
    }

    public void updateBio(BioUpdateRequest reuqest) {
        ifBioUpdated = repository.updateBio(reuqest);
    }

    public void deletePromptAnswer(int promptId) {
        ifPromptDeleted = repository.deletePrompt(promptId);
    }

    public void editPhoto() {
        ifEditPhoto.setValue(true);
    }

    public void addPrompt() {
        ifAddPrompt.setValue(true);
    }

    public LiveData<Boolean> getIfEditPhoto() {
        return ifEditPhoto;
    }

    public LiveData<Boolean> getIfAddPrompt() {
        return ifAddPrompt;
    }

    public void uploadProfilePhoto(File image) {
        uploadedImagePath = repository.uploadProfilePhoto(image);
    }
}
