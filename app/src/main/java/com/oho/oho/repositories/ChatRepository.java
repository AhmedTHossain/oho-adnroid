package com.oho.oho.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.Chat;
import com.oho.oho.responses.ChatRoom;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {
    private Context context;

    public ChatRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<Chat>> getChatHistory(int chat_id){
        MutableLiveData<List<Chat>> chatHistory = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<Chat>> call = service.getChatHistory(chat_id);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                if (response.isSuccessful()){
                    chatHistory.setValue(response.body());
                    Toast.makeText(context,"Successfully fetched chat history!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Chat>> call, @NonNull Throwable t) {
                chatHistory.setValue(null);
                Toast.makeText(context,"Failed to fetch chat history!",Toast.LENGTH_SHORT).show();
            }
        });
        return chatHistory;
    }
}
