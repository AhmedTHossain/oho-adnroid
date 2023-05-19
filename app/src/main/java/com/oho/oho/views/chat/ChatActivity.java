package com.oho.oho.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.oho.oho.R;
import com.oho.oho.adapters.ChatAdapter;
import com.oho.oho.adapters.ChatRoomAdapter;
import com.oho.oho.adapters.QuickMessageAdapter;
import com.oho.oho.databinding.ActivityChatBinding;
import com.oho.oho.databinding.ActivityFaqactivityBinding;
import com.oho.oho.interfaces.QuickMessageClickListener;
import com.oho.oho.models.ChatNotificationPayload;
import com.oho.oho.models.ChatRoomObj;
import com.oho.oho.models.JWTTokenRequest;
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

public class ChatActivity extends AppCompatActivity implements QuickMessageClickListener {

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
    private ChatRoomObj chatRoomObj;
    private int chat_id;
    private String channel_name;
    private String sender_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());
        if (getIntent().hasExtra("chatroom")) {
            selectedChatRoom = (ChatRoom) getIntent().getSerializableExtra("chatroom");
            binding.screentitle.setText(selectedChatRoom.getFullName());
            chat_id = selectedChatRoom.getId();
            channel_name = selectedChatRoom.getChannelName();
            sender_photo = selectedChatRoom.getProfilePhoto();
//            gender = selectedChatRoom.getGender();
        }

        if (getIntent().hasExtra("notificationPayload")){
            ChatNotificationPayload notificationPayload = (ChatNotificationPayload) getIntent().getSerializableExtra("notificationPayload");
            binding.screentitle.setText(notificationPayload.getSenderName());
            chat_id = Integer.parseInt(notificationPayload.getChatId());
            channel_name = notificationPayload.getChannelName();
            sender_photo = notificationPayload.getSenderPhoto();
//            gender = notificationPayload.getGender();

            Log.d("ChatActivity","inside Chat Activity");
        }

        Glide.with(this)
                .load(sender_photo)
                .placeholder(R.drawable.person_placeholder)
                .into(binding.titleImage);

        shimmerViewContainer = binding.shimmerViewContainer;
        initChatViewModel();

        binding.fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!iFSentButtonClicked && !TextUtils.isEmpty(binding.layoutInputMessage.getText())) {
                    iFSentButtonClicked = true;
                    sendNewChat(binding.layoutInputMessage.getText().toString());
                    binding.layoutInputMessage.setText("");
                    binding.layoutInputMessage.setHint("Message...");
                }
            }
        });

        binding.imageButtonShowQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, QRActivity.class);
                intent.putExtra("qrcode", qrcodeUrl);
                intent.putExtra("username", "Ahmed Tanzeer Hossain"); //TODO: Later replace with logged in user's fullname
                startActivity(intent);
            }
        });

        setQuickMessages();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation();
        if (mSocket != null) {
            mSocket.disconnect();
            mSocket.off();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mSocket != null) {
            mSocket.disconnect();
            mSocket.off();
        }
        super.onDestroy();
    }

    private void initChatViewModel() {
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        //TODO: replace with logged in user's id
        viewModel.getChatHistory(chat_id);
        Toast.makeText(this, "Fetch chats for room: " + chat_id, Toast.LENGTH_SHORT).show();
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
            binding.recyclerviewQuickmessages.setVisibility(View.VISIBLE);
        });

        viewModel.getQrCode(99, chat_id); //TODO: later replace with logged in user's id
        viewModel.qrcode.observe(this, qrcode -> {
            if (qrcode != null) {
                qrcodeUrl = qrcode;
            }
        });
        getJwtToken("tanzeerhossain@gmail.com"); //TODO: later replace the hard coded email with logged in user's email
    }

    private void setChatList(ArrayList<Chat> chatList) {
        adapter = new ChatAdapter(chatList, 99, this);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.scrollToPosition(chatList.size() - 1);
    }

    //set quick messages in recyclerview
    private void setQuickMessages() {
        // Get the string array of quick messages from the resources
        String[] myStringArray = getResources().getStringArray(R.array.quickmessage_list);
        QuickMessageAdapter quickMessageAdapter = new QuickMessageAdapter(myStringArray, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL);
        binding.recyclerviewQuickmessages.setHasFixedSize(true);
        binding.recyclerviewQuickmessages.setLayoutManager(staggeredGridLayoutManager);
        binding.recyclerviewQuickmessages.setAdapter(quickMessageAdapter);
    }

    @Override
    public void onQuickMessageClick(String message) {
        Log.d("ChatActivity", "onQuickMessageClick() called: YES");
        sendNewChat(message);
    }

    public void sendNewChat(String message) {
        mSocket.emit(chatRoomObj.getRoomName(), message);
        iFSentButtonClicked = false;
        Log.d("ChatActivity", "Socket ID:" + mSocket.id());
        iFSentButtonClicked = false;
    }

    private void getJwtToken(String email) {
        JWTTokenRequest jwtTokenRequest = new JWTTokenRequest();
        jwtTokenRequest.setEmail(email);
        viewModel.getJwtToken(jwtTokenRequest);
        viewModel.jwtToken.observe(this, jwtToken -> {
            if (jwtToken != null){
                Log.d("MessageFragment","initial jwt token: "+jwtToken);

                token = jwtToken;
                connectSocket();
            }
            else
                Toast.makeText(this, "Unable to fetch JWT Token!", Toast.LENGTH_LONG).show();
        });
    }

    private void connectSocket(){
        {
            try {
                IO.Options options = new IO.Options();
                options.auth = new HashMap<>();
                options.auth.put("token", token);

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

        mSocket.on(channel_name, new Emitter.Listener() {
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
        chatRoomObj = new ChatRoomObj();
        chatRoomObj.setRoomName(channel_name);
        chatRoomObj.setChat_id(chat_id);
        try {
            JSONObject jsonObject = new JSONObject().accumulate("roomName", chatRoomObj.getRoomName()).accumulate("chat_id", chatRoomObj.getChat_id());

            mSocket.emit("private message", jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}