package com.oho.oho.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.oho.oho.R;
import com.oho.oho.adapters.ChatAdapter;
import com.oho.oho.adapters.ChatRoomAdapter;
import com.oho.oho.databinding.ActivityChatBinding;
import com.oho.oho.databinding.ActivityFaqactivityBinding;
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

    private Socket mSocket;
    {
        try {
            IO.Options options = new IO.Options();
            options.auth = new HashMap<>();
            options.auth.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODMxNzYzOTIsImlhdCI6MTY4MzA4OTk5Miwic3ViIjo5OX0.dneixvG-WuYMYYMf0Cm6sEhnWZBGKMTCWBjG2b5cs94");

//            options.transports = new String[]{WebSocket.NAME};

            mSocket = IO.socket("http://34.232.79.30:3000", options);
        } catch (URISyntaxException e) {
            Log.d("ChatActivity","socket exception: "+ e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());
        if(getIntent().getExtras() != null)
            selectedChatRoom = (ChatRoom) getIntent().getSerializableExtra("chatroom");
        binding.screentitle.setText(selectedChatRoom.getFullName());
        shimmerViewContainer = binding.shimmerViewContainer;
        initChatViewModel();

        mSocket.connect();

        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("ChatActivity","socket connected");
            }
        });
        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Exception e = (Exception) args[0];
                Log.d("ChatActivity","socket connection error: " + e.getCause());
            }
        });

        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("ChatActivity","socket dis-connected");
            }
        });

        mSocket.on("227bc74c-5d49-4beb-8b70-1743a4b912e0", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("ChatActivity","socket connection to chatroom: Successfull!");

                String message = (String) args[0];
                Log.d("ChatActivity", "Received message: " + message);
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
        Toast.makeText(this,"Fetch chats for room: "+ selectedChatRoom.getId(), Toast.LENGTH_SHORT).show();
        viewModel.chatHistory.observe(this, chatHistory -> {
            if (chatHistory != null) {
                ArrayList<Chat> chatList= new ArrayList<>(chatHistory);
                int chatToRemove = 0;
                for (int i=0; i<chatList.size(); i++)
                    if (chatList.get(i).getChatType().equals("delegate"))
                        if (chatList.get(i).getSender() == 99)
                            chatToRemove = i;

                chatList.remove(chatToRemove);

                setChatList(chatList);
            }
            shimmerViewContainer.stopShimmerAnimation();
            shimmerViewContainer.setVisibility(View.GONE);
        });
    }

    private void setChatList(ArrayList<Chat> chatList) {
        ChatAdapter adapter = new ChatAdapter(chatList, 99, this);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.scrollToPosition(chatList.size()-1);
    }
}