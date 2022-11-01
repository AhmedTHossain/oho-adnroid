package com.oho.oho.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.oho.oho.models.Profile;
import com.oho.oho.models.Prompt;
import com.oho.oho.repositories.CompleteProfileRepository;

import java.io.File;
import java.util.List;

public class CompleteProfileViewModel extends AndroidViewModel {

    private CompleteProfileRepository repository;
    public LiveData<Boolean> ifUploaded;
    public LiveData<Boolean> ifBioUpdated;

    public Profile userProfile;

    public CompleteProfileViewModel(@NonNull Application application) {
        super(application);

        repository = new CompleteProfileRepository();
    }

    public void uploadProfilePhoto(int user_id, File file, Context context){
        ifUploaded = repository.updateUserProfilePhoto(user_id, file, getApplication().getApplicationContext());
    }

    public void updateBio(Profile profile){
        ifBioUpdated = repository.updateUserBio(profile,getApplication().getApplicationContext());
    }

    public void setUserProfile(Profile profile){
        userProfile = profile;
    }

    public Profile getUserProfile(){
        return userProfile;
    }
}
