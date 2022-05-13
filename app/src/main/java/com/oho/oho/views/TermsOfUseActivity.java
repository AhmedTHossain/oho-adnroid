package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebViewClient;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityMainBinding;
import com.oho.oho.databinding.ActivityTermsOfUseBinding;

import java.io.File;

public class TermsOfUseActivity extends AppCompatActivity {

    ActivityTermsOfUseBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityTermsOfUseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}