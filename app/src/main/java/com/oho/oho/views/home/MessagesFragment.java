package com.oho.oho.views.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.oho.oho.R;
import com.oho.oho.adapters.ChatRoomAdapter;
import com.oho.oho.databinding.FragmentMessagesBinding;
import com.oho.oho.interfaces.OnChatRoomClickListener;
import com.oho.oho.interfaces.OnMessageOptionsMenu;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.models.Profile;
import com.oho.oho.models.ReportUserRequest;
import com.oho.oho.responses.chat.ChatRoom;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.viewmodels.MessageViewModel;
import com.oho.oho.views.chat.ChatActivity;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesFragment extends Fragment implements OnChatRoomClickListener, OnMessageOptionsMenu {

    FragmentMessagesBinding binding;
    private MessageViewModel viewModel;
    private ShimmerFrameLayout shimmerViewContainer;
    private String token;
    private Profile profile;
    private ArrayList<ChatRoom> chatRoomArrayList = new ArrayList<>();
    private HelperClass helperClass = new HelperClass();

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(getLayoutInflater(), container, false);
        shimmerViewContainer = binding.shimmerViewContainer;

        HelperClass helperClass = new HelperClass();
        profile = helperClass.getProfile(requireContext());

        initMessageViewModel();

        getJwtToken("tanzeerhossain@gmail.com"); //TODO: later replace the hard coded email with logged in user's email

        // Inflate the layout for this fragment

        binding.swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllChatRooms(profile.getId());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerViewContainer.startShimmerAnimation();
        getAllChatRooms(profile.getId());
    }

    @Override
    public void onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    private void setChatRoomList(ArrayList<ChatRoom> chatRoomArrayList) {
        ChatRoomAdapter adapter = new ChatRoomAdapter(chatRoomArrayList, this, this, requireContext());

        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerview.setAdapter(adapter);
    }

    private void initMessageViewModel() {
        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        //TODO: replace with logged in user's id
        getAllChatRooms(profile.getId());
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
            if (jwtToken != null) {
                Log.d("MessageFragment", "initial jwt token: " + jwtToken);

                token = jwtToken;
            } else
                Toast.makeText(requireContext(), "Unable to fetch JWT Token!", Toast.LENGTH_LONG).show();
        });
    }

    private void getAllChatRooms(int user_id) {
        viewModel.getAllChatRooms();
        viewModel.chatRoomList.observe(getViewLifecycleOwner(), chatRoomList -> {
            if (chatRoomList != null) {
                chatRoomArrayList = new ArrayList<>(chatRoomList);
                setChatRoomList(chatRoomArrayList);
            } else {
                binding.swiperefreshlayout.setVisibility(View.GONE);
                binding.screentitle.setVisibility(View.GONE);
                binding.view.setVisibility(View.GONE);
                binding.noChatLayout.setVisibility(View.VISIBLE);
            }
            shimmerViewContainer.stopShimmerAnimation();
            shimmerViewContainer.setVisibility(View.GONE);
            binding.swiperefreshlayout.setRefreshing(false);
        });
    }

    @Override
    public void openMenu(View view, String imageUrl, String nameText) {
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
                switch (menuItem.getTitle().toString()) {
                    case "Report":
                        showReportOptionDialog(imageUrl, nameText);
                        break;
                    case "Block":
                        showBlockOptionDialog(imageUrl, nameText);
                        break;
                }
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();
    }

    private void showReportOptionDialog(String imageUrl, String nameText) {
        LayoutInflater li = LayoutInflater.from(requireContext());
        View promptsView = li.inflate(R.layout.report_profile_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        CircleImageView imageView = promptsView.findViewById(R.id.image);
        TextView nameTextView = promptsView.findViewById(R.id.name_text);

        Glide.with(requireContext())
                .load(imageUrl)
                .into(imageView);
        nameTextView.setText(nameText);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Report", null).setNegativeButton("Cancel", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonPositive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Button buttonNegative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                buttonPositive.setTextColor(ContextCompat.getColor(requireContext(), R.color.ted_image_picker_primary_pressed));

//                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.textTertiary));
                buttonNegative.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
//
//                buttonPositive.setEnabled(false);
                AppCompatCheckBox impersonationCheckBox = promptsView.findViewById(R.id.impersonation);
                AppCompatCheckBox inappropriateCheckBox = promptsView.findViewById(R.id.inappropriate);
                AppCompatCheckBox threatsOrViolenceCheckBox = promptsView.findViewById(R.id.threatsOrViolence);

                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> reasonsToReportList = new ArrayList<>();
                        if (impersonationCheckBox.isChecked())
                            reasonsToReportList.add("Impersonation");
                        if (inappropriateCheckBox.isChecked())
                            reasonsToReportList.add("Inappropriate Behavior");
                        if (threatsOrViolenceCheckBox.isChecked())
                            reasonsToReportList.add("Threats or Violence");

                        String reasons = "";
                        for (String reason : reasonsToReportList) {
                            if (reasons.equals(""))
                                reasons = reasons + reason;
                            else
                                reasons = reasons + "," + reason;
                        }

                        Toast.makeText(requireContext(), "reasons: " + reasons, Toast.LENGTH_SHORT).show();

                        ReportUserRequest reportUserRequest = new ReportUserRequest();
                        reportUserRequest.setReason(reasons);

                        for (ChatRoom chatRoom : chatRoomArrayList) {
                            if (chatRoom.getFullName().equals(nameText)){

                                String [] participantsIdArray = chatRoom.getParticipants().split(",");
                                int userId = helperClass.getProfile(requireContext()).getId();
                                for (String id: participantsIdArray)
                                    if (Integer.parseInt(id)!=userId) {
                                        Log.d("MessagesFragment","reported user id = "+Integer.valueOf(id));
                                        reportUserRequest.setReportedUserId(Integer.valueOf(id));
                                        viewModel.reportUser(reportUserRequest);
                                    }
                            }
                        }
                        alertDialog.dismiss();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }

    private void showBlockOptionDialog(String imageUrl, String nameText) {
        LayoutInflater li = LayoutInflater.from(requireContext());
        View promptsView = li.inflate(R.layout.block_profile_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        CircleImageView imageView = promptsView.findViewById(R.id.image);
        TextView nameTextView = promptsView.findViewById(R.id.name_text);

        Glide.with(requireContext())
                .load(imageUrl)
                .into(imageView);
        nameTextView.setText(nameText);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Block", null).setNegativeButton("Cancel", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonPositive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Button buttonNegative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                buttonPositive.setTextColor(ContextCompat.getColor(requireContext(), R.color.ted_image_picker_primary_pressed));

//                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.textTertiary));
                buttonNegative.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
//
//                buttonPositive.setEnabled(false);

                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }
}