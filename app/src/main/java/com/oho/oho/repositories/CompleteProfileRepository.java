package com.oho.oho.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteProfileRepository {

    MutableLiveData<Profile> latestUserProfile = new MutableLiveData<>();

    public MutableLiveData<Profile> updateUserProfile(Profile updatedUserProfile, Context context){

        Log.d("CompleteProfileRepository","bio in repository = " + updatedUserProfile.getBio());

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Profile> call = apiService.updateUser(updatedUserProfile);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                latestUserProfile.setValue(response.body());

                SharedPreferences mPrefs = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(response.body());
                prefsEditor.putString("profile", json);
                prefsEditor.apply();
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {

            }
        });
        return latestUserProfile;
    }
}
