package com.oho.oho.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.repositories.LoginRepository;
import com.oho.oho.responses.VerifyEmailResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
