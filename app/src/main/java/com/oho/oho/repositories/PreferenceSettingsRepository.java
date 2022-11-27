package com.oho.oho.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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
}
