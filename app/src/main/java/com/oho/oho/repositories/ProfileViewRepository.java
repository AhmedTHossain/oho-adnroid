package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.BioUpdateRequest;
import com.oho.oho.models.Profile;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.SwipeMessageResponse;
import com.oho.oho.responses.uploadPhoto.ProfilePhotoResponse;
import com.oho.oho.responses.profile.GetProfileResponse;
import com.oho.oho.responses.uploadPhoto.UploadProfilePhotoResponse;
import com.oho.oho.utils.HelperClass;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public ProfileViewRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<Profile> getUserProfile(int userId) {
        MutableLiveData<Profile> profile = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetProfileResponse> call = service.getUserProfile(helperClass.getJWTToken(context), userId);
        call.enqueue(new retrofit2.Callback<GetProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetProfileResponse> call, @NonNull retrofit2.Response<GetProfileResponse> response) {
                profile.setValue(response.body().getData());
            }

            @Override
            public void onFailure(@NonNull Call<GetProfileResponse> call, @NonNull Throwable t) {
                profile.setValue(null);
            }
        });

        return profile;
    }

    // DELETE PROMPT
    public MutableLiveData<Boolean> deletePrompt(int prompt_id) {
        MutableLiveData<Boolean> isDeletedSuccessfully = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<SwipeMessageResponse> call = service.deletePromptAnswer(helperClass.getJWTToken(context), prompt_id);
        call.enqueue(new retrofit2.Callback<SwipeMessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<SwipeMessageResponse> call, @NonNull retrofit2.Response<SwipeMessageResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Your answer has been deleted!", Toast.LENGTH_SHORT).show();
                    isDeletedSuccessfully.setValue(true);
                } else {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    isDeletedSuccessfully.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SwipeMessageResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                isDeletedSuccessfully.setValue(false);
            }
        });
        return isDeletedSuccessfully;
    }

    // UPDATE BIO
    public MutableLiveData<Boolean> updateBio(BioUpdateRequest reuqest) {
        MutableLiveData<Boolean> isUpdatedSuccessfully = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Profile> call = service.updateUserBio(helperClass.getJWTToken(context), reuqest);
        call.enqueue(new retrofit2.Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull retrofit2.Response<Profile> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Your bio has been updated!", Toast.LENGTH_SHORT).show();
                    isUpdatedSuccessfully.setValue(true);
                } else {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    isUpdatedSuccessfully.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                isUpdatedSuccessfully.setValue(false);
            }
        });
        return isUpdatedSuccessfully;
    }

    public MutableLiveData<String> uploadProfilePhoto(File image) {
        MutableLiveData<String> uploadedImagePath = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", image.getName(), RequestBody.create(MediaType.parse("image/*"), image));
        Call<UploadProfilePhotoResponse> call = apiService.uploadProfilePhoto(helperClass.getJWTToken(context), filePart);
        call.enqueue(new Callback<UploadProfilePhotoResponse>() {
            @Override
            public void onResponse(@NonNull Call<UploadProfilePhotoResponse> call, @NonNull Response<UploadProfilePhotoResponse> response) {
                Log.d("ProfileViewRepository","image url in profile view repository = "+response.body().getData().getPath());
                uploadedImagePath.setValue(response.body().getData().getPath());
            }

            @Override
            public void onFailure(@NonNull Call<UploadProfilePhotoResponse> call, @NonNull Throwable t) {
                uploadedImagePath.setValue(null);
            }
        });
        return uploadedImagePath;
    }
}
