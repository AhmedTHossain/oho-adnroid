package com.oho.oho.repositories;

import android.content.Context;
import android.widget.Toast;

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

    public Context context;

    public LikeYouRepository(Context context){
        this.context = context;
    }

    public MutableLiveData<List<User>> getLikedByProfiles(int user_id){
        MutableLiveData<List<User>> userProfileList = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<User>> call = apiService.getLikedByUserProfiles(String.valueOf(18));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.body()!=null)
                    userProfileList.setValue(response.body());
                Toast.makeText(context,"All profiles loaded successfully!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                userProfileList.setValue(null);
                Toast.makeText(context,"Request failed!",Toast.LENGTH_SHORT).show();
            }
        });
        return userProfileList;
    }
}
