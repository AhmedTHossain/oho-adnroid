package com.oho.oho.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.VerifyEmailResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<VerifyEmailResponse> verifyEmailResponse;

    private static final String TAG = "LoginViewModel";

    public LoginViewModel() {
        verifyEmailResponse = new  MutableLiveData<>();
    }

    public MutableLiveData<VerifyEmailResponse> getVerifyEmailResponseObserver(){
        return verifyEmailResponse;
    }

    public void verifyEmailAPICall(String email){
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<VerifyEmailResponse> call = apiService.verifyEmail(email);

        call.enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<VerifyEmailResponse> call, @NonNull Response<VerifyEmailResponse> response) {

                if (Objects.requireNonNull(response.body()).getMessage().equals("email exists"))
                    verifyEmailResponse.postValue(response.body());
                else
                    verifyEmailResponse.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<VerifyEmailResponse> call, @NonNull Throwable t) {
                verifyEmailResponse.postValue(null);
            }
        });
    }
}
