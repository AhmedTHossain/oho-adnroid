package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.Chat;
import com.oho.oho.responses.ChatRoom;
import com.oho.oho.responses.JWTTokenResponse;
import com.oho.oho.responses.MessageResponse;
import com.oho.oho.responses.QRCodeResponse;

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
                Toast.makeText(context,"Failed to fetch chat history! " + t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("ChatRepository","Failed to fetch chat history! " + t.getMessage());
            }
        });
        return chatHistory;
    }

    public MutableLiveData<String> getQRCode(int user_id, int chat_id){
        MutableLiveData<String> qrcode = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<QRCodeResponse> call = service.getQRCode(user_id,chat_id);
        call.enqueue(new Callback<QRCodeResponse>() {
            @Override
            public void onResponse(@NonNull Call<QRCodeResponse> call, @NonNull Response<QRCodeResponse> response) {
                if (response.isSuccessful()){
                    qrcode.setValue(response.body().getQrCode());
                    Log.d("ChatRepository","Successfully fetched QRCode!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<QRCodeResponse> call, @NonNull Throwable t) {
                qrcode.setValue(null);
                Toast.makeText(context,"Failed to fetch QRCode! " + t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("ChatRepository","Failed to fetch QRCode! " + t.getMessage());
            }
        });
        return qrcode;
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
