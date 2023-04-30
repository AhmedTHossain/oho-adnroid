package com.oho.oho.views.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
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

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    private ChatRoom selectedChatRoom;
    private ChatViewModel viewModel;
    private ShimmerFrameLayout shimmerViewContainer;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation();
        super.onPause();
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
    }
}