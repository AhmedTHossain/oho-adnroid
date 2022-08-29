package com.oho.oho.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.CompleteProfileAdapter;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.interfaces.OnProfilePromptClickListener;
import com.oho.oho.interfaces.OnProfilePromptDeleteListener;
import com.oho.oho.models.PromptAnswer;

import java.util.ArrayList;

public class CompleteProfileActivity extends AppCompatActivity implements OnProfilePromptClickListener, OnProfilePromptDeleteListener {

    ActivityCompleteProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());


        ArrayList<PromptAnswer> profilePromptsList = new ArrayList<>();

        for(int i=0; i<4; i++)
            profilePromptsList.add(new PromptAnswer(0,null,null,0,null,null));

        CompleteProfileAdapter adapter = new CompleteProfileAdapter(profilePromptsList,this,this,this);
        binding.recyclerviewProfile.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewProfile.setAdapter(adapter);
    }

    @Override
    public void onProfilePromptClick() {

    }

    @Override
    public void onProfilePromptDelete(int id) {

    }
}