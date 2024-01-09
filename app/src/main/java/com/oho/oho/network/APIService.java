package com.oho.oho.network;

import com.oho.oho.availability.GetAvailabilityResponse;
import com.oho.oho.models.Availability;
import com.oho.oho.models.BioUpdateRequest;
import com.oho.oho.models.BlockUnblockUser;
import com.oho.oho.models.CreateDeviceId;
import com.oho.oho.models.GetLikesOnProfileResponse;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.models.PreferenceRequest;
import com.oho.oho.models.Profile;
import com.oho.oho.models.Prompt;
import com.oho.oho.models.ReportUserRequest;
import com.oho.oho.models.Swipe;
import com.oho.oho.responses.Attendance.GetDateStatusResponse;
import com.oho.oho.responses.MessageResponse;
import com.oho.oho.responses.StoreDeviceIdResponse;
import com.oho.oho.responses.UploadProfilePhotoResponse;
import com.oho.oho.responses.UploadPromptPhotoResponse;
import com.oho.oho.responses.VerifyEmailResponse;
import com.oho.oho.responses.availability.GetAvailabilityStatusResponse;
import com.oho.oho.responses.block.GetBlockResponse;
import com.oho.oho.responses.chat.GetChatHistoryResponse;
import com.oho.oho.responses.chat.GetChatRoomsResponse;
import com.oho.oho.responses.jwttoken.GetJwtTokenResponse;
import com.oho.oho.responses.match.GetDatesLeftResponse;
import com.oho.oho.responses.match.GetRecommendationResponse;
import com.oho.oho.responses.preference.GetPreferenceResponse;
import com.oho.oho.responses.profile.GetProfileResponse;
import com.oho.oho.responses.prompt.GetAddPromptResponse;
import com.oho.oho.responses.qrcode.GetQrCodeResponse;
import com.oho.oho.responses.report.PostReportUserResponse;
import com.oho.oho.responses.upcomingdates.GetUpcomingDatesResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<Profile> updateUserBio(@Header("Authorization") String jwtToken, @Body BioUpdateRequest reuqest);

    //for getting user profile
    @GET("users/get_profile")
    Call<GetProfileResponse> getUserProfile(@Header("Authorization") String jwtToken, @Query("user_id") int user_id);

    //for uploading profile photo
    @Multipart
    @POST("upload/upload_profile_picture_v2")
    Call<UploadProfilePhotoResponse> uploadProfilePhoto(@Header("Authorization") String jwtToken, @Part MultipartBody.Part file);

    @GET("users/prompts")
    Call<List<Prompt>> getAllPrompts();

    // for uploading prompt photos (atleast 3 for profile creation)
    @Multipart
    @POST("upload/upload_picture")
    Call<List<UploadPromptPhotoResponse>> uploadPromptPhoto(@Part("user_id") RequestBody user_id, @Part("caption") RequestBody caption, @Part MultipartBody.Part image);

    // for uploading user prompts
    @Multipart
    @POST("users/prompt_answer/add")
    Call<GetAddPromptResponse> uploadPromptAnswer(@Header("Authorization") String jwtToken, @Part("prompt") RequestBody prompt, @Part("answer") RequestBody answer, @Part("user_id") RequestBody user_id, @Part("caption") RequestBody caption, @Part MultipartBody.Part image);

    // for deleting user prompts
    @DELETE("users/prompt_answers/delete")
    Call<MessageResponse> deletePromptAnswer(@Header("Authorization") String jwtToken, @Query("id") int id);

    // for adding/updating user availability
    @POST("users/availability/add")
    Call<Availability> addAvailability(@Header("Authorization") String jwtToken, @Body Availability availList);

    //for getting user availability
    @GET("users/availability/")
    Call<GetAvailabilityResponse> getAvailability(@Header("Authorization") String jwtToken);

    //swiping profile left/right
    @POST("match/swipe")
    Call<MessageResponse> swipeProfile(@Body Swipe swipeResponse);

    //get likes on profile
    @GET("users/get_likes")
    Call<GetLikesOnProfileResponse> getLikedByUserProfiles(@Header("Authorization") String jwtToken);

    //get likes on profile
    @GET("match/get_recommendations")
    Call<GetRecommendationResponse> getRecommendations(@Header("Authorization") String jwtToken);

    //get user preference
    @GET("users/get_preference")
    Call<GetPreferenceResponse> getPreference(@Header("Authorization") String jwtToken);

    //update user preference
    @PUT("users/update/preference")
    Call<GetPreferenceResponse> updatePreference(@Header("Authorization") String jwtToken, @Body PreferenceRequest preferenceResponse);

    //get number of matches/dates left
    @GET("users/get_date_count")
    Call<GetDatesLeftResponse> getNumberOfDatesLeft(@Header("Authorization") String jwtToken);

    //check if user is available for the weekend (if the user has selected any slot for the week)
    @GET("users/get_availability_status")
    Call<GetAvailabilityStatusResponse> ifAvailable(@Header("Authorization") String jwtToken);

    //get all chat rooms for the user
    @GET("chat/rooms")
    Call<GetChatRoomsResponse> getChatRooms(@Header("Authorization") String jwtToken);

    //get chat history
    @GET("chat/history")
    Call<GetChatHistoryResponse> getChatHistory(@Header("Authorization") String jwtToken, @Query("chat_id") int chat_id, @Query("page") int page);

    //store device id (FCM token) for the first time
    @POST("users/user_device_token/create")
    Call<StoreDeviceIdResponse> storeDeviceId(@Body CreateDeviceId createDeviceId);

    //update device id (FCM token) for the first time
    @PUT("users/user_device_token/update")
    Call<StoreDeviceIdResponse> updateDeviceId(@Body CreateDeviceId createDeviceId);

    //get JWT token for socket connection
    @POST("users/token")
    Call<GetJwtTokenResponse> getJwtToken(@Body JWTTokenRequest jwtTokenRequest);

    //get QRCode for date for user
    @GET("match/get_qr_code")
    Call<GetQrCodeResponse> getQRCode(@Header("Authorization") String jwtToken, @Query("chat_id") int chat_id);

    //check if the date has started
    @GET("match/check_user_presence")
    Call<GetDateStatusResponse> ifDateStarted(@Header("Authorization") String jwtToken, @Query("match_id") int match_id);

    //repport user
    @POST("users/report_user")
    Call<PostReportUserResponse> reportUser(@Header("Authorization") String jwtToken, @Body ReportUserRequest reportUserRequest);

    //get upcoming dates
    @GET("match/upcoming_dates")
    Call<GetUpcomingDatesResponse> getUpcomingDates(@Header("Authorization") String jwtToken);

    //unblock user
    @POST("users/unblock_user")
    Call<GetBlockResponse> unblockUser(@Header("Authorization") String jwtToken, @Body BlockUnblockUser user);

    //block user
    @POST("users/block_user")
    Call<GetBlockResponse> blockUser(@Header("Authorization") String jwtToken, @Body BlockUnblockUser user);
}
