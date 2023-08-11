package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.ChatRoom;
import com.oho.oho.responses.JWTTokenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageRepository {
    private Context context;

    public MessageRepository(Context context){
        this.context = context;
    }

    public MutableLiveData<List<ChatRoom>> getChatRooms(int user_id){
        MutableLiveData<List<ChatRoom>> chatRoomList = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<ChatRoom>> call = service.getChatRooms(user_id);
        call.enqueue(new Callback<List<ChatRoom>>() {
            @Override
            public void onResponse(@NonNull Call<List<ChatRoom>> call, @NonNull Response<List<ChatRoom>> response) {
                if (response.body().size()>0){
                    chatRoomList.setValue(response.body());
                    Log.d("MessageRepository","Number of chat rooms: "+response.body().size());
                } else
                    chatRoomList.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<List<ChatRoom>> call, @NonNull Throwable t) {
                chatRoomList.setValue(null);
                Toast.makeText(context,"Request failed!",Toast.LENGTH_SHORT).show();
                Log.d("MessageRepository","Request failed with code: "+t.getMessage());
            }
        });
        return chatRoomList;
    }

    public MutableLiveData<String> getJwtToken(JWTTokenRequest jwtTokenRequest){
        MutableLiveData<String> jwtToken = new MutableLiveData<>();

        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<JWTTokenResponse> call = service.getJwtToken(jwtTokenRequest);

        call.enqueue(new Callback<JWTTokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<JWTTokenResponse> call, @NonNull Response<JWTTokenResponse> response) {
                if (response.isSuccessful()){
                    jwtToken.setValue(response.body().getJwtToken());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JWTTokenResponse> call, @NonNull Throwable t) {
                jwtToken.setValue(null);
            }
        });
        return jwtToken;
    }
}
