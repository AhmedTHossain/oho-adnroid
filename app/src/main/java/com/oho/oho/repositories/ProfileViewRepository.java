package com.oho.oho.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.MessageResponse;

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

    public MutableLiveData<Boolean> deletePrompt(int prompt_id){
        MutableLiveData<Boolean> isDeletedSuccessfully = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<MessageResponse> call = service.deletePromptAnswer(prompt_id);
        call.enqueue(new retrofit2.Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull retrofit2.Response<MessageResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Your answer has been deleted!", Toast.LENGTH_SHORT).show();
                    isDeletedSuccessfully.setValue(true);
                } else {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    isDeletedSuccessfully.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                isDeletedSuccessfully.setValue(false);
            }
        });
        return isDeletedSuccessfully;
    }
}
