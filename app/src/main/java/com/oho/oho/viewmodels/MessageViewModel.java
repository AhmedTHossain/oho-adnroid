package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.models.BlockUnblockUser;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.models.ReportUserRequest;
import com.oho.oho.repositories.MessageRepository;
import com.oho.oho.responses.chat.ChatRoom;
import com.oho.oho.responses.report.ReportUserData;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {

    private final MessageRepository messageRepository;
    public LiveData<List<ChatRoom>> chatRoomList;
    public LiveData<String> jwtToken;
    public LiveData<ReportUserData> reportedUserData;
    public LiveData<Boolean> ifBlocked;
    public LiveData<Boolean> ifUnBlocked;
    public MessageViewModel(@NonNull Application application){
        super(application);
        messageRepository = new MessageRepository(getApplication().getApplicationContext());
    }

    public void getAllChatRooms(){
        chatRoomList = messageRepository.getChatRooms();
    }

    public void getJwtToken(JWTTokenRequest jwtTokenRequest){
        jwtToken = messageRepository.getJwtToken(jwtTokenRequest);
    }

    public void reportUser(ReportUserRequest reportUserRequest){
        reportedUserData = messageRepository.reportUser(reportUserRequest);
    }

    public void blockUser(BlockUnblockUser user){
        ifBlocked = messageRepository.blockUser(user);
    }

    public void unBlockUser(BlockUnblockUser user){
        ifUnBlocked = messageRepository.unblockUser(user);
    }
}
