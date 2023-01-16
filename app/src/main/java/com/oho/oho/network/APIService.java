package com.oho.oho.network;

import com.oho.oho.models.Availability;
import com.oho.oho.models.Profile;
import com.oho.oho.models.Prompt;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.models.Swipe;
import com.oho.oho.models.User;
import com.oho.oho.responses.MessageResponse;
import com.oho.oho.responses.PreferenceResponse;
import com.oho.oho.responses.UploadProfilePhotoResponse;
import com.oho.oho.responses.UploadPromptPhotoResponse;
import com.oho.oho.responses.VerifyEmailResponse;

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
import retrofit2.http.Query;

public interface APIService {

    @GET("users/verify_email")
    Call<VerifyEmailResponse> verifyEmail(@Query("email") String email);

    @POST("users/register")
    Call<Profile> createUser(@Body Profile profile);

    @PUT("users/update")
    Call<Profile> updateUser(@Body Profile profile);

    @GET("users/get_profile")
    Call<Profile> getUserProfile(@Query("user_id") int user_id);

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
    @Multipart
    @POST("users/prompt_answer/add")
    Call<PromptAnswer> uploadPromptAnswer(@Part("prompt") RequestBody prompt, @Part("answer") RequestBody answer, @Part("user_id") RequestBody user_id, @Part("caption") RequestBody caption, @Part MultipartBody.Part image);

    // for adding/updating user availability
    @POST("users/availability/add")
    Call<ArrayList<String>> addAvailability(@Query("user_id") int user_id, @Body ArrayList<String> availList);

    //for getting user availability
    @GET("users/availability/")
    Call<Availability> getAvailability(@Query("user_id") int user_id);

    //swiping profile left/right
    @POST("match/swipe")
    Call<MessageResponse> swipeProfile(@Body Swipe swipeResponse);

    //get likes on profile
    @GET("users/get_likes")
    Call<List<User>> getLikedByUserProfiles(@Query("user_id") String user_id);

    //get likes on profile
    @GET("match/get_recommendations")
    Call<List<User>> getRecommendations(@Query("user_id") String user_id);

    //get user preference
    @GET("users/get_preference")
    Call<PreferenceResponse> getPreference(@Query("user_id") String user_id);

    //update user preference
    @PUT("users/update/preference")
    Call<PreferenceResponse> updatePreference(@Body PreferenceResponse preferenceResponse);
}
