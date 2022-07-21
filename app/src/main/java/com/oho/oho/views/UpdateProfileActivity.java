package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.databinding.ActivityUpdateProfileBinding;

public class UpdateProfileActivity extends AppCompatActivity {
    private ActivityUpdateProfileBinding binding;
    private Animation animShow, animHide;

    String bio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_OHO);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animShow = AnimationUtils.loadAnimation( this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( this, R.anim.view_hide);

        bio = getIntent().getStringExtra("BIO");

        EditText aboutEditText = binding.edittextAbout;
        TextView updateBioButton = binding.buttonUpdateAbout;

        aboutEditText.setText(bio);

        aboutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0) {
                    if (!aboutEditText.getText().toString().equals(bio)) {
                        updateBioButton.setVisibility(View.VISIBLE);
                        updateBioButton.startAnimation(animShow);
                    }
                    else {
                        updateBioButton.setVisibility(View.GONE);
                        updateBioButton.startAnimation( animHide );
                    }
                }
                else {
                    updateBioButton.setVisibility(View.GONE);
                    updateBioButton.startAnimation( animHide );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}