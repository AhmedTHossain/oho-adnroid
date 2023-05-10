package com.oho.oho.views.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityChatBinding;
import com.oho.oho.databinding.ActivityQractivityBinding;

public class QRActivity extends AppCompatActivity {

    ActivityQractivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQractivityBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());
    }
}