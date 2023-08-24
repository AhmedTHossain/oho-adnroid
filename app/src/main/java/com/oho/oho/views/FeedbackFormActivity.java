package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityChatBinding;
import com.oho.oho.databinding.ActivityFeedbackFormBinding;

public class FeedbackFormActivity extends AppCompatActivity {
    private ActivityFeedbackFormBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackFormBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());
    }
}