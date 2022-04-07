package com.oho.oho.repositories;

import static com.oho.oho.utils.HelperClass.logErrorMessage;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationRepository {

    public MutableLiveData<Profile> registerNewUser(Profile userRegistrationFormData){
        Log.d("RegistrationRepository","email in repository = " + userRegistrationFormData.getEmail());
        Log.d("RegistrationRepository","name in repository = " + userRegistrationFormData.getName());

        MutableLiveData<Profile> registeredUserProfile = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Profile> call = apiService.createUser(userRegistrationFormData);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
//                registeredUserProfile.setValue(response.body());
                Log.d("RegistrationRepository","response body = "+response.body());
                Log.d("RegistrationRepository","response body = "+response.code());
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                logErrorMessage(t.getMessage());
            }
        });
        return registeredUserProfile;
    }
}
