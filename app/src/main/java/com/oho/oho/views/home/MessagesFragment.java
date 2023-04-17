package com.oho.oho.views.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.adapters.ChatRoomAdapter;
import com.oho.oho.databinding.FragmentMessagesBinding;
import com.oho.oho.responses.ChatRoom;

import java.util.ArrayList;

public class MessagesFragment extends Fragment {

    FragmentMessagesBinding binding;


    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(getLayoutInflater(),container,false);

        ArrayList<ChatRoom> chatRoomArrayList = new ArrayList<>(createDummyChatRooms());
        setChatRoomList(chatRoomArrayList);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private ArrayList<ChatRoom> createDummyChatRooms() {
        String[] user1 = {"Emily Doe", "Elizabeth Doe", "Ellie Doe", "Emma Doe",};

        String[] user1Image = {
                "https://drive.google.com/uc?id=12vzk9nZWKdz9o_yXYua57yuGTQDLa0dm",
                "https://drive.google.com/uc?id=1JuLZyHVjs6zp1oB3PJnI6BWX7wPKMNAl",
                "https://drive.google.com/uc?id=1mqKJCgZI_6w6QmPMyfKG-AaUatpsiCp9",
                "https://drive.google.com/uc?id=1uSvOWAoFoj_zWMAVeKjFKrTy3H9ynlXB"};

        String[] lastMessage = {
                "Hi Tanzeer, Glad we matched. Your profile looks amazing! Looking forward to our date at Binge Bar DC - 506 H St NE LL, Washington, DC 20002 on Friday at 7:00 pm and getting to know you more :)",
                "Running late",
                "Hi Tanzeer, Glad we matched. Your profile looks amazing! Looking forward to our date at Binge Bar DC - 506 H St NE LL, Washington, DC 20002 on Friday at 7:00 pm and getting to know you more :)",
                "See you tomorrow :)"};
        ArrayList<ChatRoom> chatRoomArrayList = new ArrayList<>();
        for (int i = 0; i < user1.length; i++) {
            ChatRoom chatRoom = new ChatRoom();

            chatRoom.setSenderFullName(user1[i]);
            chatRoom.setLastMessage(lastMessage[i]);
            chatRoom.setSenderProfilePhotoUrl(user1Image[i]);

            chatRoomArrayList.add(chatRoom);
        }

        return chatRoomArrayList;
    }

    private void setChatRoomList(ArrayList<ChatRoom> chatRoomArrayList) {
        ChatRoomAdapter adapter = new ChatRoomAdapter(chatRoomArrayList, requireContext());
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerview.setAdapter(adapter);
    }
}