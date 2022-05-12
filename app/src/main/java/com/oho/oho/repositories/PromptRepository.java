package com.oho.oho.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.oho.oho.db.DbClient;
import com.oho.oho.db.tables.UserPrompt;
import com.oho.oho.models.Prompt;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromptRepository {

    private DbClient dbClient;

    public PromptRepository(Context context){
        dbClient = DbClient.getInstance(context);
    }

    public MutableLiveData<List<Prompt>> getPromptList(){
        MutableLiveData<List<Prompt>> promptList = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<Prompt>> call = apiService.getAllPrompts();
        call.enqueue(new Callback<List<Prompt>>() {
            @Override
            public void onResponse(@NonNull Call<List<Prompt>> call, @NonNull Response<List<Prompt>> response) {
                promptList.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Prompt>> call, @NonNull Throwable t) {

            }
        });
        return promptList;
    }

    public void addPrompt(PromptAnswer promptAnswer, Context context){
        ArrayList<PromptAnswer> promptAnswerArrayList = new ArrayList<>();
        promptAnswerArrayList.add(promptAnswer);

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<String> call = apiService.uploadPrompt(promptAnswerArrayList);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Toast.makeText(context, "Prompt answer uploaded successfully!", Toast.LENGTH_SHORT).show();

                UserPrompt userPrompt = new UserPrompt();
                userPrompt.setPromptId(promptAnswer.getPromptId());
                userPrompt.setUserId(promptAnswer.getUserId());
                userPrompt.setPictureId(promptAnswer.getPictureId());
                userPrompt.setOrderNo(promptAnswer.getOrderNo());
                userPrompt.setPromptQuestion(promptAnswer.getPromptQuestion());
                userPrompt.setAnswer(promptAnswer.getAnswer());

                insert(userPrompt);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
    }

    void insert(UserPrompt userPrompt){
        dbClient.getAppDatabase().getUserPromptDao().insertPrompt(userPrompt);
    }
}
