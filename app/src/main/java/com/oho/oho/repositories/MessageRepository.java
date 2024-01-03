package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.BlockUnblockUser;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.models.ReportUserRequest;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.block.GetBlockResponse;
import com.oho.oho.responses.chat.ChatRoom;
import com.oho.oho.responses.chat.ChatRoomsData;
import com.oho.oho.responses.chat.GetChatRoomsResponse;
import com.oho.oho.responses.jwttoken.GetJwtTokenResponse;
import com.oho.oho.responses.jwttoken.JwtTokenData;
import com.oho.oho.responses.report.PostReportUserResponse;
import com.oho.oho.responses.report.ReportUserData;
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

    public MutableLiveData<ReportUserData> reportUser(ReportUserRequest reportUserRequest) {
        MutableLiveData<ReportUserData> reportedUserData = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<PostReportUserResponse> call = service.reportUser(helperClass.getJWTToken(context), reportUserRequest);
        call.enqueue(new Callback<PostReportUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<PostReportUserResponse> call, @NonNull Response<PostReportUserResponse> response) {
                if (response.body().getStatus()) {
                    reportedUserData.setValue(response.body().getData());
                    Toast.makeText(context, "You have successfully reported this user.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostReportUserResponse> call, @NonNull Throwable t) {
                reportedUserData.setValue(null);
            }
        });
        return reportedUserData;
    }

    public MutableLiveData<Boolean> blockUser(BlockUnblockUser user) {
        MutableLiveData<Boolean> ifBlocked = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetBlockResponse> call = service.blockUser(helperClass.getJWTToken(context), user);
        call.enqueue(new Callback<GetBlockResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetBlockResponse> call, @NonNull Response<GetBlockResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus())
                        ifBlocked.setValue(true);
                    else
                        ifBlocked.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetBlockResponse> call, @NonNull Throwable t) {
                ifBlocked.setValue(false);
            }
        });
        return ifBlocked;
    }

    public MutableLiveData<Boolean> unblockUser(BlockUnblockUser user) {
        MutableLiveData<Boolean> ifUnBlocked = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetBlockResponse> call = service.unblockUser(helperClass.getJWTToken(context), user);
        call.enqueue(new Callback<GetBlockResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetBlockResponse> call, @NonNull Response<GetBlockResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus())
                        ifUnBlocked.setValue(true);
                    else
                        ifUnBlocked.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetBlockResponse> call, @NonNull Throwable t) {
                ifUnBlocked.setValue(false);
            }
        });
        return ifUnBlocked;
    }
}
