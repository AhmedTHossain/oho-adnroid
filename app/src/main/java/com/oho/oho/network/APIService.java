package com.oho.oho.network;

import com.oho.oho.models.Profile;
import com.oho.oho.responses.UploadProfilePhotoResponse;
import com.oho.oho.responses.VerifyEmailResponse;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("users/verify_email")
    Call<VerifyEmailResponse> verifyEmail(@Query("email") String email);

    @POST("users/register")
    Call<Profile> createUser(@Body Profile profile);

    @PUT("users/update")
    Call<Profile> updateUser(@Body Profile profile);

    @Multipart
    @POST("upload/upload_profile_picture")
    Call<UploadProfilePhotoResponse> uploadProfilePhoto(@Part("user_id") RequestBody user_id, @Part MultipartBody.Part file);
}
