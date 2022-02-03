package com.oho.oho.network;

import com.oho.oho.responses.VerifyEmailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("users/verify_email")
    Call<VerifyEmailResponse> verifyEmail(@Query("email") String email);

}
