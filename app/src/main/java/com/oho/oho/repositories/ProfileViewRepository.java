package com.oho.oho.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;

import retrofit2.Call;

public class ProfileViewRepository {
    private Context context;

    public ProfileViewRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<Profile> getUserProfile(int userId) {
        MutableLiveData<Profile> profile = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Profile> call = service.getUserProfile(userId);
        call.enqueue(new retrofit2.Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull retrofit2.Response<Profile> response) {
                profile.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                profile.setValue(null);
            }
        });

        return profile;
    }
}
