package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.DatesLeft;
import com.oho.oho.models.Profile;
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

    public MutableLiveData<Boolean> swipeRecommendedProfile(Swipe swipeProfile) {
        MutableLiveData<Boolean> isSwipeSuccessful = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<MessageResponse> call = apiService.swipeProfile(swipeProfile);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                MessageResponse messageResponse = response.body();
                if (messageResponse != null) {
                    Log.d("SwipeRepository","swipe api response: "+ messageResponse.getMessage());
                    isSwipeSuccessful.setValue(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                Log.d("SwipeRepository","swipe api response: "+ t.getMessage());
                isSwipeSuccessful.setValue(false);
            }
        });
        return isSwipeSuccessful;
    }

    public MutableLiveData<List<Profile>> getRecommendedProfiles(String user_id){
        MutableLiveData<List<Profile>> recommendedProfilesList = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<Profile>> call =apiService.getRecommendations(user_id);
        call.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(@NonNull Call<List<Profile>> call, @NonNull Response<List<Profile>> response) {
                if (response.body()!=null)
                    recommendedProfilesList.setValue(response.body());
//                Toast.makeText(context,"All profiles loaded successfully!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<Profile>> call, @NonNull Throwable t) {
                recommendedProfilesList.setValue(null);
//                Toast.makeText(context,"Request failed!",Toast.LENGTH_SHORT).show();
                Log.d("LikeYouRepository","Request failed with code: "+t.getMessage());
            }
        });
        return recommendedProfilesList;
    }

    public MutableLiveData<Integer> getNumberOfDatesLeft(int user_id){
        MutableLiveData<Integer> date_left = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<DatesLeft> call =apiService.getNumberOfDatesLeft(user_id);
        call.enqueue(new Callback<DatesLeft>() {
            @Override
            public void onResponse(@NonNull Call<DatesLeft> call, @NonNull Response<DatesLeft> response) {
                if (response.body() != null) {
                    date_left.setValue(response.body().getDatesLeft());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DatesLeft> call, @NonNull Throwable t) {
                date_left.setValue(-1);
            }
        });
        return date_left;
    }
}
