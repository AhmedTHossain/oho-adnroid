package com.oho.oho.views.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityAvailabilitySettingsBinding;
import com.oho.oho.databinding.ActivityMainBinding;

public class AvailabilitySettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAvailabilitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_settings);

        binding = ActivityAvailabilitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Click Listeners
//        binding.buttonAddSlot2.setOnClickListener(this);
//        binding.buttonAddSlot3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}