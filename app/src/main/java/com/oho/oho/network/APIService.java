package com.oho.oho.network;

import com.oho.oho.models.Availability;
import com.oho.oho.models.CreateDeviceId;
import com.oho.oho.models.DatesLeft;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.models.Profile;
import com.oho.oho.models.BioUpdateRequest;
import com.oho.oho.models.Prompt;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.models.Swipe;
import com.oho.oho.models.User;
import com.oho.oho.responses.Chat;
import com.oho.oho.responses.ChatRoom;
import com.oho.oho.responses.CheckAvailabilityResponse;
import com.oho.oho.responses.JWTTokenResponse;
import com.oho.oho.responses.MessageResponse;
import com.oho.oho.responses.PreferenceResponse;
import com.oho.oho.responses.QRCodeResponse;
import com.oho.oho.responses.StoreDeviceIdResponse;
import com.oho.oho.responses.UploadProfilePhotoResponse;
import com.oho.oho.responses.UploadPromptPhotoResponse;
import com.oho.oho.responses.VerifyEmailResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    //for update user profile basic info
    @PUT("users/update")
    Call<Profile> updateUser(@Body Profile profile);

    // for updating only user's bio
    @PUT("users/update")
    Call<Profile> updateUserBio(@Body BioUpdateRequest reuqest);

    //for getting user profile
    @GET("users/get_profile")
    Call<Profile> getUserProfile(@Query("user_id") int user_id);

    //for uploading profile photo
    @Multipart
    @POST("upload/upload_profile_picture_v2")
    Call<UploadProfilePhotoResponse> uploadProfilePhoto(@Part("user_id") RequestBody user_id, @Part MultipartBody.Part file);

    @GET("users/prompts")
    Call<List<Prompt>> getAllPrompts();

    // for uploading prompt photos (atleast 3 for profile creation)
    @Multipart
    @POST("upload/upload_picture")
    Call<List<UploadPromptPhotoResponse>> uploadPromptPhoto(@Part("user_id") RequestBody user_id, @Part("caption") RequestBody caption, @Part MultipartBody.Part image);

    // for uploading user prompts
    @Multipart
    @POST("users/prompt_answer/add")
    Call<PromptAnswer> uploadPromptAnswer(@Part("prompt") RequestBody prompt, @Part("answer") RequestBody answer, @Part("user_id") RequestBody user_id, @Part("caption") RequestBody caption, @Part MultipartBody.Part image);

    // for deleting user prompts
    @DELETE("users/prompt_answers/delete")
    Call<MessageResponse> deletePromptAnswer(@Query("id") int id);

    // for adding/updating user availability
    @POST("users/availability/add")
    Call<Availability> addAvailability(@Query("user_id") int user_id, @Body Availability availList);

    //for getting user availability
    @GET("users/availability/")
    Call<Availability> getAvailability(@Query("user_id") int user_id);

    //swiping profile left/right
    @POST("match/swipe")
    Call<MessageResponse> swipeProfile(@Body Swipe swipeResponse);

    //get likes on profile
    @GET("users/get_likes")
    Call<List<Profile>> getLikedByUserProfiles(@Query("user_id") String user_id);

    //get likes on profile
    @GET("match/get_recommendations")
    Call<List<Profile>> getRecommendations(@Query("user_id") String user_id);

    //get number of dates left for the weekend
    @GET("users/get_date_count")
    Call<DatesLeft> getNumberOfDatesLeft(@Query("user_id") String user_id);

    //get user preference
    @GET("users/get_preference")
    Call<PreferenceResponse> getPreference(@Query("user_id") String user_id);

    //update user preference
    @PUT("users/update/preference")
    Call<PreferenceResponse> updatePreference(@Body PreferenceResponse preferenceResponse);

    //get number of matches/dates left
    @GET("users/get_date_count")
    Call<DatesLeft> getNumberOfDatesLeft(@Query("user_id") int user_id);

    //check if user is available for the weekend (if the user has selected any slot for the week)
    @GET("users/get_availability_status")
    Call<CheckAvailabilityResponse> ifAvailable(@Query("user_id") int user_id);

    //get all chat rooms for the user
    @GET("chat/rooms")
    Call<List<ChatRoom>> getChatRooms(@Query("user_id") int user_id);

    //get chat history
    @GET("chat/history")
    Call<List<Chat>> getChatHistory(@Query("chat_id") int chat_id);

    //store device id (FCM token) for the first time
    @POST("users/user_device_token/create")
    Call<StoreDeviceIdResponse> storeDeviceId(@Body CreateDeviceId createDeviceId);

    //update device id (FCM token) for the first time
    @PUT("users/user_device_token/update")
    Call<StoreDeviceIdResponse> updateDeviceId(@Body CreateDeviceId createDeviceId);

    //get JWT token for socket connection
    @POST("users/token")
    Call<JWTTokenResponse> getJwtToken(@Body JWTTokenRequest jwtTokenRequest);

    //get QRCode for date for user
    @GET("match/get_qr_code")
    Call<QRCodeResponse> getQRCode(@Query("user_id") int user_id, @Query("chat_id") int chat_id);
}
