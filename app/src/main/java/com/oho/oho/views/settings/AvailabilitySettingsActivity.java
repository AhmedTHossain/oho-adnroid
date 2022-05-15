package com.oho.oho.views.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityAvailabilitySettingsBinding;
import com.oho.oho.databinding.ActivityMainBinding;

public class AvailabilitySettingsActivity extends AppCompatActivity {

    ActivityAvailabilitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_settings);

        binding = ActivityAvailabilitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.evening_time_slots_array, R.layout.spinner_availability_list);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.spinnerFrom.setAdapter(adapter);
        binding.spinnerTo.setAdapter(adapter);
    }
}