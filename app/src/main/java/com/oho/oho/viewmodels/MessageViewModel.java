package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.repositories.MessageRepository;
import com.oho.oho.responses.ChatRoom;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {

    private final MessageRepository messageRepository;
    public LiveData<List<ChatRoom>> chatRoomList;
    public LiveData<String> jwtToken;
    public MessageViewModel(@NonNull Application application){
        super(application);
        messageRepository = new MessageRepository(getApplication().getApplicationContext());
    }

    public void getAllChatRooms(int user_id){
        chatRoomList = messageRepository.getChatRooms(user_id);
    }

    public void getJwtToken(JWTTokenRequest jwtTokenRequest){
        jwtToken = messageRepository.getJwtToken(jwtTokenRequest);
    }
}
