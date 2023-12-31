package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.NewPromptAnswer;
import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.preference.PreferenceResponse;
import com.oho.oho.responses.UploadProfilePhotoResponse;
import com.oho.oho.responses.prompt.GetAddPromptResponse;
import com.oho.oho.utils.HelperClass;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSetupRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public ProfileSetupRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<Boolean> uploadNewPromptAnswer(NewPromptAnswer newPromptAnswer, Context context){
        MutableLiveData<Boolean> ifResponseReceived = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);

        RequestBody userId = RequestBody.create(String.valueOf(newPromptAnswer.getUser_id()),MediaType.parse("text/plain"));
        RequestBody promptText = RequestBody.create(String.valueOf(newPromptAnswer.getPrompt()),MediaType.parse("text/plain"));
        RequestBody answerText = RequestBody.create(String.valueOf(newPromptAnswer.getAnswer()),MediaType.parse("text/plain"));
        RequestBody captionText = RequestBody.create(String.valueOf(newPromptAnswer.getCaption()),MediaType.parse("text/plain"));

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", newPromptAnswer.getImage().getName(), RequestBody.create(MediaType.parse("image/*"), newPromptAnswer.getImage()));
        Call<GetAddPromptResponse> call = apiService.uploadPromptAnswer(helperClass.getJWTToken(context),promptText, answerText, userId, captionText, filePart);
        call.enqueue(new Callback<GetAddPromptResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAddPromptResponse> call, @NonNull Response<GetAddPromptResponse> response) {

                ifResponseReceived.setValue(true);
            }

            @Override
            public void onFailure(@NonNull Call<GetAddPromptResponse> call, @NonNull Throwable t) {

                ifResponseReceived.setValue(false);
            }
        });
        return ifResponseReceived;
    }

    public MutableLiveData<Boolean> uploadProfilePhoto(int user_id, File image, Context applicationContext) {
        MutableLiveData<Boolean> ifResponseReceived = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        RequestBody userId = RequestBody.create(String.valueOf(user_id),MediaType.parse("text/plain"));

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", image.getName(), RequestBody.create(MediaType.parse("image/*"), image));
        Call<UploadProfilePhotoResponse> call = apiService.uploadProfilePhoto(helperClass.getJWTToken(context), filePart);
        call.enqueue(new Callback<UploadProfilePhotoResponse>() {
            @Override
            public void onResponse(@NonNull Call<UploadProfilePhotoResponse> call, @NonNull Response<UploadProfilePhotoResponse> response) {
                ifResponseReceived.setValue(true);
            }

            @Override
            public void onFailure(@NonNull Call<UploadProfilePhotoResponse> call, @NonNull Throwable t) {
                ifResponseReceived.setValue(false);
            }
        });
        return ifResponseReceived;
    }

    public MutableLiveData<Boolean> updateGenderPreference(int user_id, String interested_in, Context context){
        MutableLiveData<Boolean> ifResponseReceived = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);

        PreferenceResponse preferenceResponse = new PreferenceResponse();
        preferenceResponse.setUserId(user_id);
        preferenceResponse.setInterestedIn(interested_in);

        Call<PreferenceResponse> call = apiService.updatePreference(preferenceResponse);
        call.enqueue(new Callback<PreferenceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PreferenceResponse> call, @NonNull Response<PreferenceResponse> response) {
                ifResponseReceived.setValue(true);
                Log.d("ProfileSetupRepository","gender update: successful");
            }

            @Override
            public void onFailure(@NonNull Call<PreferenceResponse> call, @NonNull Throwable t) {
                ifResponseReceived.setValue(false);
                Log.d("ProfileSetupRepository","gender update: failed");
            }
        });
        return ifResponseReceived;
    }

    public MutableLiveData<Profile> registerUser(Profile profile){
        MutableLiveData<Profile>  uploadedProfile = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);

        Call<Profile> call = apiService.createUser(profile);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                uploadedProfile.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                uploadedProfile.setValue(null);
            }
        });
        return uploadedProfile;
    }
}
