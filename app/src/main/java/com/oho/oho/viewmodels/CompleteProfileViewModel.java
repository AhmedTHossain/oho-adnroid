package com.oho.oho.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.oho.oho.repositories.CompleteProfileRepository;

import java.io.File;

public class CompleteProfileViewModel extends AndroidViewModel {

    private CompleteProfileRepository repository;

    public CompleteProfileViewModel(@NonNull Application application) {
        super(application);

        repository = new CompleteProfileRepository();
    }

    public void uploadProfilePhoto(int user_id, File file, Context context){
        repository.updateUserProfilePhoto(user_id, file, getApplication().getApplicationContext());
    }
}
