package com.oho.oho.views.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityAvailabilitySettingsBinding;
import com.oho.oho.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class AvailabilitySettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAvailabilitySettingsBinding binding;
    ArrayList<String> selectedSlotsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_settings);

        binding = ActivityAvailabilitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedSlotsArray = new ArrayList<>();

        //Click Listeners
        binding.buttonClearSlots.setOnClickListener(this);
        binding.buttonSaveSlots.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_clear_slots:
                binding.friSlot1.setChecked(false);
                binding.friSlot2.setChecked(false);
                binding.friSlot3.setChecked(false);
                binding.satSlot1.setChecked(false);
                binding.satSlot2.setChecked(false);
                binding.satSlot3.setChecked(false);
                binding.sunSlot1.setChecked(false);
                binding.sunSlot2.setChecked(false);
                break;
            case R.id.button_save_slots:
                saveAvailability();
                break;
        }
    }

    private void saveAvailability() {
        //Call update availability API
        Toast.makeText(getApplicationContext(), "Your slots for the weekend has been saved!", Toast.LENGTH_SHORT).show();
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        String day = "";

        switch (view.getId()) {
            case R.id.fri_slot_1:
            case R.id.fri_slot_2:
            case R.id.fri_slot_3:
                day = "Fri";
                break;
            case R.id.sat_slot_1:
            case R.id.sat_slot_2:
            case R.id.sat_slot_3:
                day = "Sat";
                break;
            case R.id.sun_slot_1:
            case R.id.sun_slot_2:
                day = "Sun";
                break;
        }

        String slot = day + ", " + ((CheckBox) view).getText().toString();
        if (checked)
            selectedSlotsArray.add(slot);
        else
            selectedSlotsArray.remove(slot);

        Log.d("AvailabilitySettingsActivity","selected slots = "+selectedSlotsArray.toString());
    }
}