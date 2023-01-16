package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Swipe;
import com.oho.oho.models.User;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.MessageResponse;
import com.oho.oho.responses.VerifyEmailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeRepository {

    private Context context;

    public SwipeRepository(Context context) {
        this.context = context;
    }

    public void swipeRecommendedProfile(Swipe swipeProfile) {
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<MessageResponse> call = apiService.swipeProfile(swipeProfile);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                MessageResponse messageResponse = response.body();
                if (messageResponse != null) {
                    Log.d("SwipeRepository","swipe api response: "+ messageResponse.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                Log.d("SwipeRepository","swipe api response: "+ t.getMessage());
            }
        });
    }

    public MutableLiveData<List<User>> getRecommendedProfiles(String user_id){
        MutableLiveData<List<User>> recommendedProfilesList = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<User>> call =apiService.getRecommendations(user_id);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.body()!=null)
                    recommendedProfilesList.setValue(response.body());
                Toast.makeText(context,"All profiles loaded successfully!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                recommendedProfilesList.setValue(null);
                Toast.makeText(context,"Request failed!",Toast.LENGTH_SHORT).show();
                Log.d("LikeYouRepository","Request failed with code: "+t.getMessage());
            }
        });
        return recommendedProfilesList;
    }
}
