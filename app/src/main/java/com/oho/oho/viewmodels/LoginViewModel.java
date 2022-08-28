package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.oho.oho.models.Profile;
import com.oho.oho.repositories.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository loginRepository;
    public LiveData<FirebaseUser> authenticatedUserLiveData;
    public LiveData<Profile> userProfileData;

    public LoginViewModel(Application application) {
        super(application);
        loginRepository = new LoginRepository();
    }

    public void signInWithGoogle(AuthCredential googleAuthCredential) {
        authenticatedUserLiveData = loginRepository.firebaseSignInWithGoogle(googleAuthCredential);
    }

    public void checkIfUserExists(String authenticatedUserEmail){
        userProfileData = loginRepository.checkIfUserEmailExists(authenticatedUserEmail);
    }
}
