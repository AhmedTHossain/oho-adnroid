package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.databinding.ActivityUpdateProfileBinding;

public class UpdateProfileActivity extends AppCompatActivity {
    ActivityUpdateProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_OHO);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edittextAbout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0)
                    binding.buttonUpdateAbout.setVisibility(View.VISIBLE);
                else
                    binding.buttonUpdateAbout.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}