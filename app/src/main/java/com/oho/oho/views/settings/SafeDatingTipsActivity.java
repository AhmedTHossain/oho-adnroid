package com.oho.oho.views.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivitySafeDatingTipsBinding;
import com.oho.oho.databinding.ActivityTermsOfUseBinding;

public class SafeDatingTipsActivity extends AppCompatActivity {
    ActivitySafeDatingTipsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivitySafeDatingTipsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}