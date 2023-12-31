package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.preference.GetPreferenceResponse;
import com.oho.oho.responses.preference.PreferenceResponse;
import com.oho.oho.responses.profile.GetProfileResponse;
import com.oho.oho.utils.HelperClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferenceSettingsRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public PreferenceSettingsRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<PreferenceResponse> getUserPreference() {
        MutableLiveData<PreferenceResponse> preferenceResponse = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetPreferenceResponse> call = apiService.getPreference(helperClass.getJWTToken(context));
        call.enqueue(new Callback<GetPreferenceResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPreferenceResponse> call, @NonNull Response<GetPreferenceResponse> response) {
                preferenceResponse.setValue(response.body().getData());
                Log.d("PreferenceSettingsRepository", "preference retrieved = YES");
            }

            @Override
            public void onFailure(@NonNull Call<GetPreferenceResponse> call, @NonNull Throwable t) {
                preferenceResponse.setValue(null);
                Log.d("PreferenceSettingsRepository", "preference retrieved = NO with response: " + t.getMessage());
            }
        });

        return preferenceResponse;
    }

    public MutableLiveData<String> getUserLocation(int user_id) {
        MutableLiveData<String> userLocation = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetProfileResponse> call = apiService.getUserProfile(helperClass.getJWTToken(context), user_id);
        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetProfileResponse> call, @NonNull Response<GetProfileResponse> response) {
                if (response.body() != null) {
                    String location = response.body().getData().getCity() + ", " + response.body().getData().getState();
                    userLocation.setValue(location);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetProfileResponse> call, @NonNull Throwable t) {
                userLocation.setValue("N/A");
            }
        });
        return userLocation;
    }

    public MutableLiveData<Boolean> updatePreference(PreferenceResponse preferences) {
        MutableLiveData<Boolean> ifPreferenceUpdated = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<PreferenceResponse> call = apiService.updatePreference(preferences);
        call.enqueue(new Callback<PreferenceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PreferenceResponse> call, @NonNull Response<PreferenceResponse> response) {
                if (response.body() != null)
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
