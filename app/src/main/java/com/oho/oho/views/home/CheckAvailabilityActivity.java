package com.oho.oho.views.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.databinding.ActivityCheckAvailabilityBinding;
import com.oho.oho.databinding.ActivityMainBinding;
import com.oho.oho.views.settings.AvailabilitySettingsActivity;

public class CheckAvailabilityActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityCheckAvailabilityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityCheckAvailabilityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // click listeners
        binding.buttonStartMatching.setOnClickListener(this);
        binding.buttonNotReady.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start_matching:
                startActivity(new Intent(this, AvailabilitySettingsActivity.class));

                storeAvailabilityConsent(1);
                break;
            case R.id.button_not_ready:
                startActivity(new Intent(this, MainActivity.class));

                storeAvailabilityConsent(0);
                break;
        }
    }

    private void storeAvailabilityConsent(int available){
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("available", available);
        editor.commit();
    }
}