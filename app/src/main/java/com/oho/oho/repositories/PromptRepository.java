package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Prompt;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.prompt.GetAddPromptResponse;
import com.oho.oho.utils.HelperClass;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromptRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public PromptRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<Prompt>> getPromptList() {
        MutableLiveData<List<Prompt>> promptList = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<Prompt>> call = apiService.getAllPrompts();
        call.enqueue(new Callback<List<Prompt>>() {
            @Override
            public void onResponse(@NonNull Call<List<Prompt>> call, @NonNull Response<List<Prompt>> response) {
                promptList.setValue(response.body());
                Log.d("PromptRepository", "number of prompts = " + response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Prompt>> call, @NonNull Throwable t) {

            }
        });
        return promptList;
    }

    public MutableLiveData<Boolean> uploadUserPromptAnswer(String prompt, String answer, int user_id, String caption, File image, Context context) {
        MutableLiveData<Boolean> ifResponseReceived = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(user_id));
        RequestBody promptText = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(prompt));
        RequestBody answerText = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(answer));
        RequestBody captionText = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(caption));

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", image.getName(), RequestBody.create(MediaType.parse("image/*"), image));

        Call<GetAddPromptResponse> call = apiService.uploadPromptAnswer(helperClass.getJWTToken(context), promptText, answerText, captionText, filePart);
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
}
