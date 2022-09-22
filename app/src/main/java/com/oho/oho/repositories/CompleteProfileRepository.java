package com.oho.oho.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.UploadProfilePhotoResponse;
import com.oho.oho.responses.UploadPromptPhotoResponse;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteProfileRepository {

    MutableLiveData<Profile> latestUserProfile = new MutableLiveData<>();

    public MutableLiveData<Profile> updateUserProfile(Profile updatedUserProfile, Context context){

        Log.d("CompleteProfileRepository","bio in repository = " + updatedUserProfile.getBio());

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Profile> call = apiService.updateUser(updatedUserProfile);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                latestUserProfile.setValue(response.body());

                SharedPreferences mPrefs = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(response.body());
                prefsEditor.putString("profile", json);
                prefsEditor.apply();
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {

            }
        });
        return latestUserProfile;
    }

    public  MutableLiveData<Boolean> updateUserProfilePhoto(int id, File file, Context context){
        MutableLiveData<Boolean> ifResponseReceived = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        Call<UploadProfilePhotoResponse> call = apiService.uploadProfilePhoto(user_id,filePart);
        call.enqueue(new Callback<UploadProfilePhotoResponse>() {
            @Override
            public void onResponse(@NonNull Call<UploadProfilePhotoResponse> call, @NonNull Response<UploadProfilePhotoResponse> response) {
                Log.d("CompleteProfileRepository", "respone code = "+response.code());
                Toast.makeText(context, "Profile photo uploaded successfully!", Toast.LENGTH_SHORT).show();

                SharedPreferences mPrefs = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();

                prefsEditor.putInt("profile_picture_id", response.body().getProfilePictureId());
                prefsEditor.apply();

                ifResponseReceived.setValue(true);
            }

            @Override
            public void onFailure(@NonNull Call<UploadProfilePhotoResponse> call, @NonNull Throwable t) {
                Log.d("CompleteProfileRepository", "respone code = "+t.getMessage());

                ifResponseReceived.setValue(true);
            }
        });

        return ifResponseReceived;
    }

    public MutableLiveData<Integer> uploadUserPromptPhoto(int id, String captionText, File file, Context context){

        MutableLiveData<Integer> uploadedPhotoId = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));
        RequestBody caption = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(captionText));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        Call<List<UploadPromptPhotoResponse>> call = apiService.uploadPromptPhoto(user_id,caption,filePart);
        call.enqueue(new Callback<List<UploadPromptPhotoResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<UploadPromptPhotoResponse>> call, @NonNull Response<List<UploadPromptPhotoResponse>> response) {
                Log.d("CompleteProfileRepository","CompleteProfileRepository called = YES");
                Toast.makeText(context, "Photo uploaded successfully! and photo id = "+response.body().get(0).getPictureId(), Toast.LENGTH_SHORT).show();
                uploadedPhotoId.setValue(response.body().get(0).getPictureId());
            }

            @Override
            public void onFailure(@NonNull Call<List<UploadPromptPhotoResponse>> call, @NonNull Throwable t) {

            }
        });
        return uploadedPhotoId;
    }

}
