package com.oho.oho.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.CompleteProfileAdapter;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.interfaces.AddPromptListener;
import com.oho.oho.interfaces.OnProfilePromptClickListener;
import com.oho.oho.interfaces.OnProfilePromptDeleteListener;
import com.oho.oho.models.PromptAnswer;

import java.util.ArrayList;

public class CompleteProfileActivity extends AppCompatActivity implements OnProfilePromptClickListener, OnProfilePromptDeleteListener, AddPromptListener {

    ActivityCompleteProfileBinding binding;

    CompleteProfileAdapter adapter;
    ArrayList<PromptAnswer> profilePromptsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        for(int i=0; i<4; i++)
            profilePromptsList.add(new PromptAnswer(0,null,null,0,null,null));

        adapter = new CompleteProfileAdapter(profilePromptsList,this,this, this,this);
        binding.recyclerviewProfile.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewProfile.setAdapter(adapter);
    }

    @Override
    public void onProfilePromptClick() {

    }

    @Override
    public void onProfilePromptDelete(int id) {

    }

    @Override
    public void addPrompt() {
        profilePromptsList.add(new PromptAnswer(0,null,null,0,null,null));
        adapter.notifyItemInserted(profilePromptsList.size());
    }
}