package com.oho.oho.views.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityCheckAvailabilityBinding;
import com.oho.oho.databinding.ActivityMainBinding;

public class CheckAvailabilityActivity extends AppCompatActivity {
    ActivityCheckAvailabilityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityCheckAvailabilityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}