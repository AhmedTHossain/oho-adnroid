package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.models.Swipe;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.SwipeMessageResponse;
import com.oho.oho.responses.match.GetDatesLeftResponse;
import com.oho.oho.responses.match.GetRecommendationResponse;
import com.oho.oho.responses.swipe.PostSwipeResponse;
import com.oho.oho.utils.HelperClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeRepository {

    private Context context;
    private HelperClass helperClass = new HelperClass();

    public SwipeRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<Boolean> swipeRecommendedProfile(Swipe swipeProfile) {
        MutableLiveData<Boolean> isSwipeSuccessful = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<PostSwipeResponse> call = apiService.swipeProfile(helperClass.getJWTToken(context),swipeProfile);
        call.enqueue(new Callback<PostSwipeResponse>() {
            @Override
            public void onResponse(@NonNull Call<PostSwipeResponse> call, @NonNull Response<PostSwipeResponse> response) {
                if (response.isSuccessful()) {
                    SwipeMessageResponse messageResponse = response.body().getData();

                    Log.d("SwipeRepository", "swipe api response: " + response.message());
                    isSwipeSuccessful.setValue(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostSwipeResponse> call, @NonNull Throwable t) {
                Log.d("SwipeRepository", "swipe api response: " + t.getMessage());
                isSwipeSuccessful.setValue(false);
            }
        });
        return isSwipeSuccessful;
    }

    public MutableLiveData<List<Profile>> getRecommendedProfiles() {
        MutableLiveData<List<Profile>> recommendedProfilesList = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetRecommendationResponse> call = apiService.getRecommendations(helperClass.getJWTToken(context));
        call.enqueue(new Callback<GetRecommendationResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetRecommendationResponse> call, @NonNull Response<GetRecommendationResponse> response) {
                if (response.body() != null)
                    recommendedProfilesList.setValue(response.body().getData());
//                Toast.makeText(context,"All profiles loaded successfully!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<GetRecommendationResponse> call, @NonNull Throwable t) {
                recommendedProfilesList.setValue(null);
//                Toast.makeText(context,"Request failed!",Toast.LENGTH_SHORT).show();
                Log.d("LikeYouRepository", "Request failed with code: " + t.getMessage());
            }
        });
        return recommendedProfilesList;
    }

    public MutableLiveData<Integer> getNumberOfDatesLeft() {
        MutableLiveData<Integer> date_left = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetDatesLeftResponse> call = apiService.getNumberOfDatesLeft(helperClass.getJWTToken(context));
        call.enqueue(new Callback<GetDatesLeftResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDatesLeftResponse> call, @NonNull Response<GetDatesLeftResponse> response) {
                if (response.body() != null) {
                    date_left.setValue(response.body().getData().getDatesLeft());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetDatesLeftResponse> call, @NonNull Throwable t) {
                date_left.setValue(-1);
            }
        });
        return date_left;
    }
}
