package com.oho.oho.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.oho.oho.R;
import com.oho.oho.adapters.ChatAdapter;
import com.oho.oho.adapters.ChatRoomAdapter;
import com.oho.oho.databinding.ActivityChatBinding;
import com.oho.oho.databinding.ActivityFaqactivityBinding;
import com.oho.oho.models.ChatRoomObj;
import com.oho.oho.responses.Chat;
import com.oho.oho.responses.ChatRoom;
import com.oho.oho.viewmodels.ChatViewModel;
import com.oho.oho.viewmodels.MessageViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    private ChatRoom selectedChatRoom;
    private ChatViewModel viewModel;
    private ShimmerFrameLayout shimmerViewContainer;

    public ArrayList<Chat> chatList;

    private Socket mSocket;
    public ChatAdapter adapter;
    private String token;
    private boolean iFSentButtonClicked = false;
    private String qrcodeUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());
        if (getIntent().getExtras() != null) {
            selectedChatRoom = (ChatRoom) getIntent().getSerializableExtra("chatroom");
            token = (String) getIntent().getStringExtra("token");
        }
        binding.screentitle.setText(selectedChatRoom.getFullName());
        shimmerViewContainer = binding.shimmerViewContainer;
        initChatViewModel();

        {
            try {
                IO.Options options = new IO.Options();
                options.auth = new HashMap<>();
                options.auth.put("token", token);

//            options.transports = new String[]{WebSocket.NAME};

                mSocket = IO.socket("http://34.232.79.30:3000", options);
            } catch (URISyntaxException e) {
                Log.d("ChatActivity", "socket exception: " + e.getMessage());
            }
        }
        mSocket.connect();

        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("ChatActivity", "socket connected");
            }
        });
        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Exception e = (Exception) args[0];
                Log.d("ChatActivity", "socket connection error: " + e.getCause());
            }
        });

        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("ChatActivity", "socket dis-connected");
            }
        });

        mSocket.on(selectedChatRoom.getChannelName(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("ChatActivity", "socket connection to chatroom: Successfull!");

                try {
                    JSONObject messageJSON = new JSONObject(args[0].toString());
                    String message = messageJSON.getString("message");
                    int user_id = messageJSON.getInt("user_id");
                    int time = messageJSON.getInt("time");

                    Log.d("ChatActivity", "Received message body: " + message);
                    Log.d("ChatActivity", "Received message from user: " + user_id);

                    Chat newChat = new Chat();
                    newChat.setChatType("private");
                    newChat.setSender(user_id);
                    newChat.setCreatedAt(time);
                    newChat.setBody(message);

                    chatList.add(newChat);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setChatList(chatList); //TODO: Find if there's a better way to refresh the chat list on new message to the socket
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        });

//        mSocket.on("message", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                String message = (String) args[0];
//                // Process the message here
//                Log.d("ChatActivity", "Received message: " + message);
//            }
//        });


//        mSocket.disconnect();

        binding.fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!iFSentButtonClicked && !TextUtils.isEmpty(binding.layoutInputMessage.getText())) {
                    iFSentButtonClicked = true;

                    ChatRoomObj chatRoomObj = new ChatRoomObj();
                    chatRoomObj.setRoomName("227bc74c-5d49-4beb-8b70-1743a4b912e0");
                    chatRoomObj.setChat_id(21);


                    try {
                        JSONObject jsonObject = new JSONObject().accumulate("roomName", chatRoomObj.getRoomName()).accumulate("chat_id", chatRoomObj.getChat_id());

                        mSocket.emit("private message", jsonObject);
                        mSocket.emit(chatRoomObj.getRoomName(), binding.layoutInputMessage.getText().toString());

                        binding.layoutInputMessage.setText("");
                        binding.layoutInputMessage.setHint("Message...");

                        iFSentButtonClicked = false;
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        binding.imageButtonShowQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this,QRActivity.class);
                intent.putExtra("qrcode",qrcodeUrl);
                intent.putExtra("username","Ahmed Tanzeer Hossain"); //TODO: Later replace with logged in user's fullname
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation();
        mSocket.disconnect();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mSocket.disconnect();
        super.onDestroy();
    }

    private void initChatViewModel() {
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        //TODO: replace with logged in user's id
        viewModel.getChatHistory(selectedChatRoom.getId());
        Toast.makeText(this, "Fetch chats for room: " + selectedChatRoom.getId(), Toast.LENGTH_SHORT).show();
        viewModel.chatHistory.observe(this, chatHistory -> {
            if (chatHistory != null) {
                chatList = new ArrayList<>(chatHistory);
                int chatToRemove = 0;
                for (int i = 0; i < chatList.size(); i++)
                    if (chatList.get(i).getChatType().equals("delegate"))
                        if (chatList.get(i).getSender() == 99)
                            chatToRemove = i;

                chatList.remove(chatToRemove);

                setChatList(chatList);
            }
            shimmerViewContainer.stopShimmerAnimation();
            shimmerViewContainer.setVisibility(View.GONE);
        });

        viewModel.getQrCode(99,selectedChatRoom.getId()); //TODO: later replace with logged in user's id
        viewModel.qrcode.observe(this, qrcode ->{
            if (qrcode!=null){
                qrcodeUrl = qrcode;
            }
        });
    }

    private void setChatList(ArrayList<Chat> chatList) {
        adapter = new ChatAdapter(chatList, 99, this);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.scrollToPosition(chatList.size() - 1);
    }
}