package com.oho.oho.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Prompt;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromptRepository {

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
}
