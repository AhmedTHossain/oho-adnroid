package com.oho.oho.repositories;

import static com.oho.oho.utils.HelperClass.logErrorMessage;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.VerifyEmailResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public MutableLiveData<FirebaseUser> firebaseSignInWithGoogle(AuthCredential googleAuthCredential) {
        MutableLiveData<FirebaseUser> authenticatedUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener(authTask -> {
            if (authTask.isSuccessful()) {
                boolean isNewUser = authTask.getResult().getAdditionalUserInfo().isNewUser();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    authenticatedUserMutableLiveData.setValue(firebaseUser);
                }
            } else {
                logErrorMessage(Objects.requireNonNull(authTask.getException()).getMessage());
            }
        });
        return authenticatedUserMutableLiveData;
    }

    public MutableLiveData<Profile> checkIfUserEmailExists(String authenticatedUserEmail) {
        MutableLiveData<Profile> checkedUserProfileMutableData = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<VerifyEmailResponse> call = apiService.verifyEmail(authenticatedUserEmail);

        call.enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<VerifyEmailResponse> call, @NonNull Response<VerifyEmailResponse> response) {

                if (response.isSuccessful())
                    if (response.body() != null) {
                        checkedUserProfileMutableData.setValue(response.body().getData());
                    } else
                        checkedUserProfileMutableData.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<VerifyEmailResponse> call, @NonNull Throwable t) {
                logErrorMessage(t.getMessage());
            }
        });
        return checkedUserProfileMutableData;
    }
}
