package com.oho.oho.network;

import com.oho.oho.models.Profile;
import com.oho.oho.models.Prompt;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.responses.UploadProfilePhotoResponse;
import com.oho.oho.responses.UploadPromptPhotoResponse;
import com.oho.oho.responses.VerifyEmailResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @GET("users/prompts")
    Call<List<Prompt>> getAllPrompts();

    // for uploading prompt photos (atleast 3 for profile creation)
    @Multipart
    @POST("upload/upload_picture")
    Call<List<UploadPromptPhotoResponse>> uploadPromptPhoto(@Part("user_id") RequestBody user_id, @Part("caption") RequestBody caption, @Part MultipartBody.Part file);

    // for uploading user prompts
    @POST("users/prompt_answer/add")
    Call<String> uploadPrompt(@Body ArrayList<PromptAnswer> promptAnswer);

    // for adding/updating user availability
    @POST("users/availability/add")
    Call<ArrayList<String>> addAvailability(@Query("user_id") int user_id, @Body ArrayList<String> availList);

    //for getting user availability
    @GET("users/availability/")
    Call<ArrayList<String>> getAvailability(@Query("user_id") int user_id);
}
