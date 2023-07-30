package com.oho.oho.views.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.oho.oho.R;
import com.oho.oho.adapters.ChatRoomAdapter;
import com.oho.oho.databinding.FragmentMessagesBinding;
import com.oho.oho.interfaces.OnChatRoomClickListener;
import com.oho.oho.interfaces.OnMessageOptionsMenu;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.responses.ChatRoom;
import com.oho.oho.viewmodels.LikeYouVIewModel;
import com.oho.oho.viewmodels.MessageViewModel;
import com.oho.oho.views.chat.ChatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MessagesFragment extends Fragment implements OnChatRoomClickListener, OnMessageOptionsMenu {

    FragmentMessagesBinding binding;
    private MessageViewModel viewModel;
    private ShimmerFrameLayout shimmerViewContainer;
    private String token;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(getLayoutInflater(), container, false);
        shimmerViewContainer = binding.shimmerViewContainer;
        initMessageViewModel();

        getJwtToken("tanzeerhossain@gmail.com"); //TODO: later replace the hard coded email with logged in user's email

        // Inflate the layout for this fragment

        binding.swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllChatRooms(187); //TODO: later replace this with logged in user's id
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerViewContainer.startShimmerAnimation();
        getAllChatRooms(187); //TODO: later replace this with logged in user's id
    }

    @Override
    public void onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    private void setChatRoomList(ArrayList<ChatRoom> chatRoomArrayList) {
        ChatRoomAdapter adapter = new ChatRoomAdapter(chatRoomArrayList, this, this, requireContext());

        Collections.reverse(chatRoomArrayList);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerview.setAdapter(adapter);
    }

    private void initMessageViewModel() {
        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        //TODO: replace with logged in user's id
        getAllChatRooms(187); //TODO: later replace this with logged in user's id
    }

    @Override
    public void onChatRoomClick(ChatRoom chatRoom) {
        Intent intent = new Intent(requireActivity(), ChatActivity.class);
        intent.putExtra("chatroom", chatRoom); //where chatroom is an instance of ChatRoom object
        intent.putExtra("token", token); //a newly generated token has been sent to ChatActivity
        startActivity(intent);
    }

    private void getJwtToken(String email) {
        JWTTokenRequest jwtTokenRequest = new JWTTokenRequest();
        jwtTokenRequest.setEmail(email);
        viewModel.getJwtToken(jwtTokenRequest);
        viewModel.jwtToken.observe(requireActivity(), jwtToken -> {
            if (jwtToken != null){
                Log.d("MessageFragment","initial jwt token: "+jwtToken);

                token = jwtToken;
            }
            else
                Toast.makeText(requireContext(), "Unable to fetch JWT Token!", Toast.LENGTH_LONG).show();
        });
    }

    private void getAllChatRooms(int user_id){
        viewModel.getAllChatRooms(user_id);
        viewModel.chatRoomList.observe(getViewLifecycleOwner(), chatRoomList -> {
            if (chatRoomList != null) {
                ArrayList<ChatRoom> chatRoomArrayList = new ArrayList<>(chatRoomList);
                setChatRoomList(chatRoomArrayList);
            }
            shimmerViewContainer.stopShimmerAnimation();
            shimmerViewContainer.setVisibility(View.GONE);
            binding.swiperefreshlayout.setRefreshing(false);
        });
    }

    @Override
    public void openMenu(View view) {
        // Initializing the popup menu and giving the reference as current context
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.message_options_menu, popupMenu.getMenu());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                Toast.makeText(requireContext(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();
    }
}