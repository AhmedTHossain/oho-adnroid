package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.repositories.ChatRepository;
import com.oho.oho.responses.Chat;
import com.oho.oho.responses.chat.ChatHistoryData;
import com.oho.oho.responses.qrcode.QRCodeData;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private final ChatRepository chatRepository;
    public LiveData<ChatHistoryData> chatHistory;
    public LiveData<QRCodeData> qrcode;

    public LiveData<String> jwtToken;

    public LiveData<Boolean> ifDateStarted;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        chatRepository = new ChatRepository(getApplication().getApplicationContext());
    }

    public void getChatHistory(int chat_id, int page){
        chatHistory = chatRepository.getChatHistory(chat_id, page);
    }

    public void getQrCode(int user_id,int chat_id){
        qrcode = chatRepository.getQRCode(chat_id);
    }

    public void getJwtToken(JWTTokenRequest jwtTokenRequest){
        jwtToken = chatRepository.getJwtToken(jwtTokenRequest);
    }

    public void checkDateStatus(int match_id){
        ifDateStarted = chatRepository.checkIfDateStarted(match_id);
    }
}
