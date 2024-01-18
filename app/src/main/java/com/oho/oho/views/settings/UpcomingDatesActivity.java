package com.oho.oho.views.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.oho.oho.R;
import com.oho.oho.adapters.UpcomingDatesAdapter;
import com.oho.oho.databinding.ActivityUpcomingDatesBinding;
import com.oho.oho.interfaces.OnChatRoomClickListener;
import com.oho.oho.models.UpcomingDate;
import com.oho.oho.responses.chat.ChatRoom;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.viewmodels.UpcomingDatesViewModel;
import com.oho.oho.views.chat.ChatActivity;

import java.util.ArrayList;

public class UpcomingDatesActivity extends AppCompatActivity implements OnChatRoomClickListener {
    ActivityUpcomingDatesBinding binding;
    private UpcomingDatesViewModel viewModel;
    private ArrayList<UpcomingDate> upcomingDateArrayList = new ArrayList<>();
    private UpcomingDatesAdapter adapter;
    private ShimmerFrameLayout shimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityUpcomingDatesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        shimmerLayout = binding.shimmerViewContainer;

        initViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerLayout.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerLayout.stopShimmerAnimation();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UpcomingDatesViewModel.class);
        viewModel.getUpcomingDates();
        viewModel.upcomingDatesData.observe(this, upcomingDatesData -> {
            if (upcomingDatesData.getData().size()>0) {
                upcomingDateArrayList.addAll(upcomingDatesData.getData());
                setUpcomingDatesList();
            } else {
                Toast.makeText(UpcomingDatesActivity.this,"inside no dates",Toast.LENGTH_SHORT).show();
                //TODO: show a placeholder image when no upcoming date is available
                shimmerLayout.stopShimmerAnimation();
                shimmerLayout.setVisibility(View.GONE);
                binding.noDataLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setUpcomingDatesList() {
        adapter = new UpcomingDatesAdapter(upcomingDateArrayList, this,this);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);

        shimmerLayout.stopShimmerAnimation();
        shimmerLayout.setVisibility(View.GONE);
        binding.recyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onChatRoomClick(ChatRoom chatRoom) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("chatroom", chatRoom); //where chatroom is an instance of ChatRoom object
        intent.putExtra("token", new HelperClass().getJWTToken(this)); //a newly generated token has been sent to ChatActivity
        startActivity(intent);
    }
}