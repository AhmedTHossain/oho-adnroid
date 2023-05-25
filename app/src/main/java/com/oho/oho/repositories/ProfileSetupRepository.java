package com.oho.oho.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.NewPromptAnswer;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSetupRepository {
    private Context context;

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
        Call<PromptAnswer> call = apiService.uploadPromptAnswer(promptText, answerText, userId, captionText, filePart);
        call.enqueue(new Callback<PromptAnswer>() {
            @Override
            public void onResponse(@NonNull Call<PromptAnswer> call, @NonNull Response<PromptAnswer> response) {

                ifResponseReceived.setValue(true);
            }

            @Override
            public void onFailure(@NonNull Call<PromptAnswer> call, @NonNull Throwable t) {

                ifResponseReceived.setValue(false);
            }
        });
        return ifResponseReceived;
    }
}
