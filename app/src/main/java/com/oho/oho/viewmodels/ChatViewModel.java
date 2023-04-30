package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.repositories.ChatRepository;
import com.oho.oho.responses.Chat;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private final ChatRepository chatRepository;
    public LiveData<List<Chat>> chatHistory;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        chatRepository = new ChatRepository(getApplication().getApplicationContext());
    }

    public void getChatHistory(int chat_id){
        chatHistory = chatRepository.getChatHistory(chat_id);
    }
}
