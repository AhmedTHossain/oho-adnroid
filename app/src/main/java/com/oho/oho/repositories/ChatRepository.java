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
import com.oho.oho.responses.chat.ChatHistoryData;
import com.oho.oho.responses.chat.GetChatHistoryResponse;
import com.oho.oho.responses.jwttoken.GetJwtTokenResponse;
import com.oho.oho.responses.qrcode.GetQrCodeResponse;
import com.oho.oho.responses.qrcode.QRCodeData;
import com.oho.oho.responses.jwttoken.JwtTokenData;
import com.oho.oho.utils.HelperClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public ChatRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<ChatHistoryData> getChatHistory(int chat_id, int page){
        MutableLiveData<ChatHistoryData> chatHistory = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetChatHistoryResponse> call = service.getChatHistory(helperClass.getJWTToken(context),chat_id,page);
        call.enqueue(new Callback<GetChatHistoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetChatHistoryResponse> call, @NonNull Response<GetChatHistoryResponse> response) {
                if (response.isSuccessful()){
                    chatHistory.setValue(response.body().getData());
                    Toast.makeText(context,"Successfully fetched chat history!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetChatHistoryResponse> call, @NonNull Throwable t) {
                chatHistory.setValue(null);
                Toast.makeText(context,"Failed to fetch chat history! " + t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("ChatRepository","Failed to fetch chat history! " + t.getMessage());
            }
        });
        return chatHistory;
    }

    public MutableLiveData<String> getQRCode(int chat_id){
        MutableLiveData<String> qrcode = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetQrCodeResponse> call = service.getQRCode(helperClass.getJWTToken(context),chat_id);
        call.enqueue(new Callback<GetQrCodeResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetQrCodeResponse> call, @NonNull Response<GetQrCodeResponse> response) {
                if (response.isSuccessful()){
                    qrcode.setValue(response.body().getData().getQrCode());
                    Log.d("ChatRepository","Successfully fetched QRCode!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetQrCodeResponse> call, @NonNull Throwable t) {
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
        Call<GetJwtTokenResponse> call = service.getJwtToken(jwtTokenRequest);

        call.enqueue(new Callback<GetJwtTokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetJwtTokenResponse> call, @NonNull Response<GetJwtTokenResponse> response) {
                Log.d("ChatRepository","jwt token of the user = "+response.code());
                if (response.isSuccessful()){
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
