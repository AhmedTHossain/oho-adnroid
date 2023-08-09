package com.oho.oho.views.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityChatBinding;
import com.oho.oho.databinding.ActivityPreferenceSettingsBinding;

import java.util.ArrayList;

public class PreferenceSettingsActivity extends AppCompatActivity {

    private ActivityPreferenceSettingsBinding binding;
    private ArrayList<String> ageList = new ArrayList();
    private ArrayList<String> heightList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPreferenceSettingsBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());

        setHeightSpinner();
        setAgeSpinner();
    }

    private void setHeightSpinner() {
        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 12; j++)
                heightList.add(i + "." + j + " ft");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, heightList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.heightSpinnerMin.setAdapter(adapter);
        binding.heightSpinnerMax.setAdapter(adapter);

        binding.heightSpinnerMin.setSelection(13);
        binding.heightSpinnerMax.setSelection(19);
    }

    private void setAgeSpinner() {
        for (int i = 18; i < 66; i++) {
            if (i < 65)
                ageList.add(i + " years");
            else
                ageList.add(i + "+ years");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ageList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.ageSpinnerMin.setAdapter(adapter);
        binding.ageSpinnerMax.setAdapter(adapter);

        binding.ageSpinnerMin.setSelection(5);
        binding.ageSpinnerMax.setSelection(10);
    }
}