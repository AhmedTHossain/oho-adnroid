package com.oho.oho.views.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicyActivity extends AppCompatActivity {
    ActivityPrivacyPolicyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = com.oho.oho.databinding.ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}