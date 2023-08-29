package com.oho.oho.views.settings;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.oho.oho.R;
import com.oho.oho.adapters.PreferenceInputAdapter;
import com.oho.oho.databinding.ActivityPreferenceSettingsBinding;
import com.oho.oho.models.PreferenceInput;
import com.oho.oho.models.Profile;
import com.oho.oho.responses.PreferenceResponse;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.viewmodels.PreferenceSettingsViewModel;
import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;

public class PreferenceSettingsActivity extends AppCompatActivity {

    private ActivityPreferenceSettingsBinding binding;
    private ArrayList<String> ageList = new ArrayList();
    private ArrayList<String> heightList = new ArrayList();
    private PreferenceSettingsViewModel viewModel;
    private Profile profile;
    private PreferenceResponse preferences;

    private ArrayList<PreferenceInput> genderList = new ArrayList<>();
    private PreferenceInputAdapter genderInputAdapter;
    private ArrayList<PreferenceInput> distanceList = new ArrayList<>();
    private PreferenceInputAdapter distanceInputAdapter;
    private ArrayList<PreferenceInput> educationList = new ArrayList<>();
    private PreferenceInputAdapter educationInputAdapter;
    private ArrayList<PreferenceInput> raceList = new ArrayList<>();
    private PreferenceInputAdapter raceInputAdapter;
    private ArrayList<PreferenceInput> religionList = new ArrayList<>();
    private PreferenceInputAdapter religionInputAdapter;
    private ArrayList<PreferenceInput> cuisineList = new ArrayList<>();
    private PreferenceInputAdapter cuisineInputAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPreferenceSettingsBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());

        HelperClass helperClass = new HelperClass();
        profile = helperClass.getProfile(this);

        initViewModel();

        setGenderRecyclerview();
        setDistanceRecylcerview();
        setEducationRecyclerView();
        setRaceRecyclerview();
        setReligionRecyclerview();
        setCuisineRecyclerview();

        setHeightSpinner();
        setAgeSpinner();
    }

    private void setCuisineRecyclerview() {
        // Get the string array from resources
        String[] cuisineArray = getResources().getStringArray(R.array.cuisine_list);
        for (String cuisine: cuisineArray){
            PreferenceInput preferenceInput = new PreferenceInput(cuisine,false);
            cuisineList.add(preferenceInput);
        }

        cuisineInputAdapter = new PreferenceInputAdapter(cuisineList,this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewCuisine.setLayoutManager(layoutManager);
        binding.recyclerviewCuisine.setAdapter(cuisineInputAdapter);
    }

    private void setReligionRecyclerview() {
        // Get the string array from resources
        String[] religionArray = getResources().getStringArray(R.array.religion_list);
        for (String religion: religionArray){
            PreferenceInput preferenceInput = new PreferenceInput(religion,false);
            religionList.add(preferenceInput);
        }

        religionInputAdapter = new PreferenceInputAdapter(religionList,this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewReligion.setLayoutManager(layoutManager);
        binding.recyclerviewReligion.setAdapter(religionInputAdapter);
    }

    private void setRaceRecyclerview() {
        // Get the string array from resources
        String[] raceArray = getResources().getStringArray(R.array.ethnicity_list);
        for (String race: raceArray){
            PreferenceInput preferenceInput = new PreferenceInput(race,false);
            raceList.add(preferenceInput);
        }

        raceInputAdapter = new PreferenceInputAdapter(raceList,this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewRace.setLayoutManager(layoutManager);
        binding.recyclerviewRace.setAdapter(raceInputAdapter);
    }

    private void setEducationRecyclerView() {
        // Get the string array from resources
        String[] educationArray = getResources().getStringArray(R.array.education_list);
        for (String education: educationArray){
            PreferenceInput preferenceInput = new PreferenceInput(education,false);
            educationList.add(preferenceInput);
        }

        educationInputAdapter = new PreferenceInputAdapter(educationList,this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewEducation.setLayoutManager(layoutManager);
        binding.recyclerviewEducation.setAdapter(educationInputAdapter);
    }

    private void setDistanceRecylcerview() {
        // Get the string array from resources
        String[] distanceArray = getResources().getStringArray(R.array.distance_list);
        for (String distance: distanceArray){
            PreferenceInput preferenceInput = new PreferenceInput(distance,false);
            distanceList.add(preferenceInput);
        }

        distanceInputAdapter = new PreferenceInputAdapter(distanceList,this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewDistance.setLayoutManager(layoutManager);
        binding.recyclerviewDistance.setAdapter(distanceInputAdapter);
    }

    private void setGenderRecyclerview() {
        // Get the string array from resources
        String[] genderArray = getResources().getStringArray(R.array.gender_list);
        for (String gender: genderArray){
            PreferenceInput preferenceInput = new PreferenceInput(gender,false);
            genderList.add(preferenceInput);
        }

        genderInputAdapter = new PreferenceInputAdapter(genderList,this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewGender.setLayoutManager(layoutManager);
        binding.recyclerviewGender.setAdapter(genderInputAdapter);
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
            }
        });
    }

    private void setHeightSpinner() {
        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 12; j++)
                heightList.add(i + "." + j + " ft");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, heightList);

        List<IconSpinnerItem> iconSpinnerItems = new ArrayList<>();

        for (String st : heightList)
            iconSpinnerItems.add(new IconSpinnerItem(st, null));

        IconSpinnerAdapter adapterMinHeight = new IconSpinnerAdapter(binding.heightSpinnerMin);
        binding.heightSpinnerMin.setSpinnerAdapter(adapterMinHeight);
        binding.heightSpinnerMin.setItems(iconSpinnerItems);

        IconSpinnerAdapter adapterMaxHeight = new IconSpinnerAdapter(binding.heightSpinnerMax);
        binding.heightSpinnerMax.setSpinnerAdapter(adapterMaxHeight);
        binding.heightSpinnerMax.setItems(iconSpinnerItems);

        int height = adapter.getCount() * adapter.getViewTypeCount() * 20;

        binding.heightSpinnerMin.setSpinnerPopupHeight(height);
        binding.heightSpinnerMax.setSpinnerPopupHeight(height);
    }

    private void setAgeSpinner() {
        for (int i = 18; i < 66; i++) {
            if (i < 65)
                ageList.add(i + " yrs");
            else
                ageList.add(i + "+ yrs");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ageList);

        List<IconSpinnerItem> iconSpinnerItems = new ArrayList<>();
        for (String st : ageList)
            iconSpinnerItems.add(new IconSpinnerItem(st, null));

        IconSpinnerAdapter adapterMinAge = new IconSpinnerAdapter(binding.ageSpinnerMin);
        binding.ageSpinnerMin.setSpinnerAdapter(adapterMinAge);
        binding.ageSpinnerMin.setItems(iconSpinnerItems);

        IconSpinnerAdapter adapterMaxAge = new IconSpinnerAdapter(binding.ageSpinnerMax);
        binding.ageSpinnerMax.setSpinnerAdapter(adapterMaxAge);
        binding.ageSpinnerMax.setItems(iconSpinnerItems);

        int height = adapter.getCount() * adapter.getViewTypeCount() * 20;

        binding.ageSpinnerMin.setSpinnerPopupHeight(height);
        binding.ageSpinnerMax.setSpinnerPopupHeight(height);

    }
}