package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.DatesLeft;
import com.oho.oho.models.GetLikesOnProfileResponse;
import com.oho.oho.models.Profile;
import com.oho.oho.models.User;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.match.GetDatesLeftResponse;
import com.oho.oho.utils.HelperClass;

import java.util.List;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeYouRepository {

    public Context context;
    private HelperClass helperClass = new HelperClass();

    public LikeYouRepository(Context context){
        this.context = context;
    }

    public MutableLiveData<List<Profile>> getLikedByProfiles(String jwtToken){
        MutableLiveData<List<Profile>> userProfileList = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetLikesOnProfileResponse> call = apiService.getLikedByUserProfiles(jwtToken);
        call.enqueue(new Callback<GetLikesOnProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetLikesOnProfileResponse> call, @NonNull Response<GetLikesOnProfileResponse> response) {
                if (response.body()!=null)
                    userProfileList.setValue(response.body().getData());
//                Toast.makeText(context,"All profiles loaded successfully! = "+ response.body().size(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<GetLikesOnProfileResponse> call, @NonNull Throwable t) {
                userProfileList.setValue(null);
                Toast.makeText(context,"Request failed!",Toast.LENGTH_SHORT).show();
                Log.d("LikeYouRepository","Request failed with code: "+t.getMessage());
            }
        });
        return userProfileList;
    }

    public MutableLiveData<Boolean> getNumberOfDatesAvailable(){
        MutableLiveData<Boolean> isDateAvailable = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetDatesLeftResponse> call = apiService.getNumberOfDatesLeft(helperClass.getJWTToken(context));
        call.enqueue(new Callback<GetDatesLeftResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDatesLeftResponse> call, @NonNull Response<GetDatesLeftResponse> response) {

                if (response.body() != null) {
                    if (response.body().getData().getDatesLeft() > 0)
                        isDateAvailable.setValue(true);
                    else
                        isDateAvailable.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetDatesLeftResponse> call, @NonNull Throwable t) {

            }
        });
        return isDateAvailable;
    }
}
