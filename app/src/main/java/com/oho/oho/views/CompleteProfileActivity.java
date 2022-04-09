package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.oho.oho.R;
import com.oho.oho.models.Profile;

public class CompleteProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        //fetching newly registered profile
        Gson gson = new Gson();
        String profile = getIntent().getStringExtra("profile");
        Profile userProfile = gson.fromJson(profile, Profile.class);
    }
}