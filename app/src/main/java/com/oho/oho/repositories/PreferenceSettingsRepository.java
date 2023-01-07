package com.oho.oho.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.models.Prompt;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.PreferenceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferenceSettingsRepository {

    public PreferenceSettingsRepository(Context context){

    }

    public MutableLiveData<PreferenceResponse> getUserPreference(String user_id){
        MutableLiveData<PreferenceResponse> preferenceResponse = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<PreferenceResponse> call = apiService.getPreference(user_id);
        call.enqueue(new Callback<PreferenceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PreferenceResponse> call, @NonNull Response<PreferenceResponse> response) {
                preferenceResponse.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<PreferenceResponse> call, @NonNull Throwable t) {

            }
        });

        return preferenceResponse;
    }

    public MutableLiveData<String> getUserLocation(int user_id){
        MutableLiveData<String> userLocation = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Profile> call = apiService.getUserProfile(18);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                if (response.body() != null) {
                    String location = response.body().getCity() + ", " + response.body().getState();
                    userLocation.setValue(location);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                userLocation.setValue("N/A");
            }
        });
        return userLocation;
    }

    public MutableLiveData<Boolean> updatePreference(PreferenceResponse preferences){
        MutableLiveData<Boolean> ifPreferenceUpdated = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<PreferenceResponse> call = apiService.updatePreference(preferences);
        call.enqueue(new Callback<PreferenceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PreferenceResponse> call, @NonNull Response<PreferenceResponse> response) {
                if (response.body()!=null)
                    ifPreferenceUpdated.setValue(true);
            }

            @Override
            public void onFailure(@NonNull Call<PreferenceResponse> call, @NonNull Throwable t) {
                ifPreferenceUpdated.setValue(true);
            }
        });
        return ifPreferenceUpdated;
    }
}
