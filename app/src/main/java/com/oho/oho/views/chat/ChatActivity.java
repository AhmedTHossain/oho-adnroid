package com.oho.oho.views.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityChatBinding;
import com.oho.oho.databinding.ActivityFaqactivityBinding;
import com.oho.oho.responses.ChatRoom;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    private ChatRoom selectedChatRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());
        if(getIntent().getExtras() != null)
            selectedChatRoom = (ChatRoom) getIntent().getSerializableExtra("chatroom");
        binding.screentitle.setText(selectedChatRoom.getFullName());
    }
}