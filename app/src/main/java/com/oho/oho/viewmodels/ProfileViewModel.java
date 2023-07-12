package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.repositories.ProfileViewRepository;

public class ProfileViewModel extends AndroidViewModel {
    public LiveData<Profile> userProfile;
    private ProfileViewRepository repository;
    private MutableLiveData<Integer> promptToDelete = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifEditBio = new MutableLiveData<>();

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
}
