package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.chat.ChatRoom;
import com.oho.oho.responses.chat.ChatRoomsData;
import com.oho.oho.responses.chat.GetChatRoomsResponse;
import com.oho.oho.responses.jwttoken.GetJwtTokenResponse;
import com.oho.oho.responses.jwttoken.JwtTokenData;
import com.oho.oho.utils.HelperClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public MessageRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<ChatRoom>> getChatRooms() {
        MutableLiveData<List<ChatRoom>> chatRoomList = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetChatRoomsResponse> call = service.getChatRooms(helperClass.getJWTToken(context));
        call.enqueue(new Callback<GetChatRoomsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetChatRoomsResponse> call, @NonNull Response<GetChatRoomsResponse> response) {
//                if (response.body().size()>0){
//                    chatRoomList.setValue(response.body());
//                    Log.d("MessageRepository","Number of chat rooms: "+response.body().size());
//                } else
//                    chatRoomList.setValue(null);
                if (response.isSuccessful()) {
                    GetChatRoomsResponse chatRoomsResponse = response.body();
                    ChatRoomsData chatRoomsData = chatRoomsResponse.getData();
                    if (chatRoomsData.getData().size() > 0) {
                        chatRoomList.setValue(chatRoomsData.getData());
                        Log.d("MessageRepository", "Number of chat rooms: " + chatRoomsData.getData().size());
                    } else
                        chatRoomList.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetChatRoomsResponse> call, @NonNull Throwable t) {
                chatRoomList.setValue(null);
                Toast.makeText(context, "Request failed!", Toast.LENGTH_SHORT).show();
                Log.d("MessageRepository", "Request failed with code: " + t.getMessage());
            }
        });
        return chatRoomList;
    }

    public MutableLiveData<String> getJwtToken(JWTTokenRequest jwtTokenRequest) {
        MutableLiveData<String> jwtToken = new MutableLiveData<>();

        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetJwtTokenResponse> call = service.getJwtToken(jwtTokenRequest);

        call.enqueue(new Callback<GetJwtTokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetJwtTokenResponse> call, @NonNull Response<GetJwtTokenResponse> response) {
                if (response.isSuccessful()) {
                    JwtTokenData jwtTokenData = new JwtTokenData();
                    jwtTokenData = response.body().getData();
                    jwtToken.setValue(jwtTokenData.getJwtToken());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetJwtTokenResponse> call, @NonNull Throwable t) {
                jwtToken.setValue(null);
            }
        });
        return jwtToken;
    }
}
