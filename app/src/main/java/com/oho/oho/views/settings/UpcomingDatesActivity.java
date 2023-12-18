package com.oho.oho.views.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityUpcomingDatesBinding;

public class UpcomingDatesActivity extends AppCompatActivity {
    ActivityUpcomingDatesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityUpcomingDatesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}