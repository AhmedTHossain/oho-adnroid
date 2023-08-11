package com.oho.oho.views.settings;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityPreferenceSettingsBinding;
import com.oho.oho.models.Profile;
import com.oho.oho.responses.PreferenceResponse;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.viewmodels.PreferenceSettingsViewModel;

import java.util.ArrayList;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;

public class PreferenceSettingsActivity extends AppCompatActivity {

    private ActivityPreferenceSettingsBinding binding;
    private ArrayList<String> ageList = new ArrayList();
    private ArrayList<String> heightList = new ArrayList();
    private PreferenceSettingsViewModel viewModel;
    private Profile profile;
    private PreferenceResponse preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPreferenceSettingsBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());

        HelperClass helperClass = new HelperClass();
        profile = helperClass.getProfile(this);

        initViewModel();

        setHeightSpinner();
        setAgeSpinner();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PreferenceSettingsViewModel.class);
        getPreferences();
    }

    private void getPreferences() {

        viewModel.getProfilePreference(String.valueOf(profile.getId()));
        viewModel.preferenceResponse.observe(this, preferenceResponse -> {
            if (preferenceResponse != null) {
                Toast.makeText(this, "Preferred gender: " + preferenceResponse.getRace(), Toast.LENGTH_LONG).show();
                preferences = preferenceResponse;

                setGenderPreference(preferences.getInterestedIn());
                setDistancePreference(preferences.getDistance());
                setEducationPreference(preferences.getEducation());
                setRacePreference(preferences.getRace());
                setReligionPreference(preferences.getReligion());
            }
        });
    }

    private void setReligionPreference(String religion) {
        String buttonId;
        if (religion.equals("Open to all"))
            buttonId = "button_" + religion.toLowerCase().replace(" ", "")+"religion";
        else
            buttonId = "button_" + religion.toLowerCase().replace(" ", "");
        Toast.makeText(this, "button id = " + buttonId, Toast.LENGTH_SHORT).show();
        selectRadioButton(buttonId, "religion");
    }

    private void setRacePreference(String race) {
        String buttonId;
        if (race.equals("Open to all"))
            buttonId = "button_" + race.toLowerCase().replace(" ", "")+"race";
        else
            buttonId = "button_" + race.toLowerCase().replace(" ", "");
        Toast.makeText(this, "button id = " + buttonId, Toast.LENGTH_SHORT).show();
        selectRadioButton(buttonId, "race");
    }

    private void setEducationPreference(String education) {
        String buttonId = "button_" + education.toLowerCase().replace(" ", "");
        Toast.makeText(this, "button id = " + buttonId, Toast.LENGTH_SHORT).show();
        selectRadioButton(buttonId, "education");
    }

    private void setDistancePreference(Integer distance) {
        String buttonId = "button_" + distance;
        selectRadioButton(buttonId, "distance");
    }

    private void setGenderPreference(String interestedIn) {
        String buttonId = "button_" + interestedIn.toLowerCase();
        selectRadioButton(buttonId, "gender");
    }

    private void selectRadioButton(String buttonId, String buttonGroup) {
        int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
        if (resourceId != 0) {
            ThemedButton selectedRadioButton = findViewById(resourceId);
            if (!selectedRadioButton.isSelected()) {
                switch (buttonGroup) {
                    case "gender":
                        binding.buttonGroupGender.selectButton(resourceId);
                        break;
                    case "distance":
                        binding.buttonGroupDistance.selectButton(resourceId);
                        break;
                    case "education":
                        binding.buttonGroupEducation.selectButton(resourceId);
                        break;
                    case "race":
                        binding.buttonGroupRace.selectButton(resourceId);
                        break;
                    case "religion":
                        binding.buttonGroupReligion.selectButton(resourceId);
                        break;
                }

            }
        } else
            Log.d("PreferenceSettingsActivity", "button id could not be found");
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