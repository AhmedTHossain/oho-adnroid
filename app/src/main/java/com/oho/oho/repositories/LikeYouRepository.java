package com.oho.oho.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.User;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;

import java.util.List;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeYouRepository {
    public MutableLiveData<List<User>> getLikedByProfiles(int user_id){
        MutableLiveData<List<User>> userProfileList = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<User>> call = apiService.getLikedByUserProfiles(String.valueOf(18));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.body()!=null)
                    userProfileList.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {

            }
        });
        return userProfileList;
    }
}
