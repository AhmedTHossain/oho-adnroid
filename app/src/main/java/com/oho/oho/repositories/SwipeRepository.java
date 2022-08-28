package com.oho.oho.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.oho.oho.models.Swipe;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.MessageResponse;
import com.oho.oho.responses.VerifyEmailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeRepository {
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
}
