package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.oho.oho.R;
import com.oho.oho.adapters.ProfilePromptAdapter;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.databinding.ActivityUpdateProfileBinding;
import com.oho.oho.models.PromptAnswer;

import java.util.ArrayList;

public class UpdateProfileActivity extends AppCompatActivity {
    private ActivityUpdateProfileBinding binding;
    private Animation animShow, animHide;
    private ProfilePromptAdapter adapter;

    String bio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_OHO);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);

        bio = getIntent().getStringExtra("BIO");

        EditText aboutEditText = binding.edittextAbout;
        TextView updateBioButton = binding.buttonUpdateAbout;
//        TextView prompt1 = binding.textPromp1;
//        TextView answer1 = binding.textAnswer1;
//
//        prompt1.setText(getIntent().getStringExtra("PROMPT1"));
//        answer1.setText(getIntent().getStringExtra("ANSWER1"));

        RecyclerView recyclerView = binding.recyclerviewPrompts;

        //TODO: now using a hard coded prompt list which will later be replaced by response of the api

        ArrayList<PromptAnswer> promptList = new ArrayList<>();

        PromptAnswer promptAnswer1 = new PromptAnswer();
        promptAnswer1.setPromptQuestion("the most adventurous thing I did was...");
        promptAnswer1.setAnswer("Sky Diving from 22,000 ft");

        PromptAnswer promptAnswer2 = new PromptAnswer();
        promptAnswer2.setPromptQuestion("in a partner, I'm looking for...");
        promptAnswer2.setAnswer("Commitment &amp; honesty");

        PromptAnswer promptAnswer3 = new PromptAnswer();
        promptAnswer3.setPromptQuestion("whiskey or wine?");
        promptAnswer3.setAnswer("Whiskey");

        promptList.add(promptAnswer1);
        promptList.add(promptAnswer2);
        promptList.add(promptAnswer3);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("UpdateProfile", "prompt list size = " + promptList.size());
        adapter = new ProfilePromptAdapter(this, promptList);

        recyclerView.setAdapter(adapter);

        aboutEditText.setText(bio);

        aboutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (!aboutEditText.getText().toString().equals(bio)) {

                        if (updateBioButton.getVisibility() != View.VISIBLE) {
                            updateBioButton.startAnimation(animShow);
                            updateBioButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        updateBioButton.setVisibility(View.GONE);
                        updateBioButton.startAnimation(animHide);
                    }
                } else {
                    updateBioButton.setVisibility(View.GONE);
                    updateBioButton.startAnimation(animHide);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}