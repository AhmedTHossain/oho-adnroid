package com.oho.oho.repositories;

import static com.oho.oho.utils.HelperClass.logErrorMessage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationRepository {

    public MutableLiveData<Profile> registerNewUser(Profile userRegistrationFormData, Context context){
        Log.d("RegistrationRepository","email in repository = " + userRegistrationFormData.getEmail());
        Log.d("RegistrationRepository","name in repository = " + userRegistrationFormData.getName());

        MutableLiveData<Profile> registeredUserProfile = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Profile> call = apiService.createUser(userRegistrationFormData);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
//                registeredUserProfile.setValue(response.body());
//                Log.d("RegistrationRepository","age in response body = "+response.body().getAge());
//                Log.d("RegistrationRepository","response body = "+response.code());
//
//                SharedPreferences mPrefs = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
//                SharedPreferences.Editor prefsEditor = mPrefs.edit();
//                Gson gson = new Gson();
//                String json = gson.toJson(response.body());
//                prefsEditor.putString("profile", json);
//                prefsEditor.apply();

                Toast.makeText(context,"Profile created Successfully!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                logErrorMessage(t.getMessage());
            }
        });
        return registeredUserProfile;
    }
}
