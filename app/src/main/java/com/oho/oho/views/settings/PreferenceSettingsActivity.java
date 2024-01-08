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
import com.oho.oho.interfaces.OnPreferenceSelected;
import com.oho.oho.models.PreferenceInput;
import com.oho.oho.models.PreferenceRequest;
import com.oho.oho.models.Profile;
import com.oho.oho.responses.preference.PreferenceResponse;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.viewmodels.PreferenceSettingsViewModel;
import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;

import java.util.ArrayList;
import java.util.List;

public class PreferenceSettingsActivity extends AppCompatActivity implements OnPreferenceSelected {

    private ActivityPreferenceSettingsBinding binding;
    private ArrayList<String> ageList = new ArrayList();
    private ArrayList<String> heightList = new ArrayList();
    private PreferenceSettingsViewModel viewModel;
    private Profile profile;
    private PreferenceResponse preferences;
    String initialStringEducation = "";
    String initialStringReligion = "";

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
    private ArrayList<PreferenceInput> budgetList = new ArrayList<>();
    private PreferenceInputAdapter budgetInputAdapter;
    private PreferenceRequest updatedPreferences = new PreferenceRequest();
    private String distanceSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPreferenceSettingsBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());

        HelperClass helperClass = new HelperClass();
        profile = helperClass.getProfile(this);

        setGenderRecyclerview();
        setDistanceRecylcerview();
        setEducationRecyclerView();
        setRaceRecyclerview();
        setReligionRecyclerview();
        setCuisineRecyclerview();
        setBudgetRecyclerview();

        setHeightSpinner();
        setAgeSpinner();

        initViewModel();
    }

    private void setBudgetRecyclerview() {
        // Get the string array from resources
        String[] budgetArray = getResources().getStringArray(R.array.budget_list);
        for (String budget : budgetArray) {
            PreferenceInput preferenceInput = new PreferenceInput(budget, false);
            budgetList.add(preferenceInput);
        }

        budgetInputAdapter = new PreferenceInputAdapter(budgetList, this, viewModel, "budget", this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewBudget.setLayoutManager(layoutManager);
        binding.recyclerviewBudget.setAdapter(budgetInputAdapter);
    }

    private void setCuisineRecyclerview() {
        // Get the string array from resources
        String[] cuisineArray = getResources().getStringArray(R.array.cuisine_list);
        for (String cuisine : cuisineArray) {
            PreferenceInput preferenceInput = new PreferenceInput(cuisine, false);
            cuisineList.add(preferenceInput);
        }
        cuisineInputAdapter = new PreferenceInputAdapter(cuisineList, this, viewModel, "cuisine", this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewCuisine.setLayoutManager(layoutManager);
        binding.recyclerviewCuisine.setAdapter(cuisineInputAdapter);
    }

    private void setReligionRecyclerview() {
        // Get the string array from resources
        String[] religionArray = getResources().getStringArray(R.array.religion_list);
        for (String religion : religionArray) {
            PreferenceInput preferenceInput = new PreferenceInput(religion, false);
            religionList.add(preferenceInput);
        }

        religionInputAdapter = new PreferenceInputAdapter(religionList, this, viewModel, "religion", this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewReligion.setLayoutManager(layoutManager);
        binding.recyclerviewReligion.setAdapter(religionInputAdapter);
    }

    private void setRaceRecyclerview() {
        // Get the string array from resources
        String[] raceArray = getResources().getStringArray(R.array.ethnicity_list);
        for (String race : raceArray) {
            PreferenceInput preferenceInput = new PreferenceInput(race, false);
            raceList.add(preferenceInput);
        }

        raceInputAdapter = new PreferenceInputAdapter(raceList, this, viewModel, "race", this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewRace.setLayoutManager(layoutManager);
        binding.recyclerviewRace.setAdapter(raceInputAdapter);
    }

    private void setEducationRecyclerView() {
        // Get the string array from resources
        String[] educationArray = getResources().getStringArray(R.array.education_list);
        for (String education : educationArray) {
            PreferenceInput preferenceInput = new PreferenceInput(education, false);
            educationList.add(preferenceInput);
        }

        educationInputAdapter = new PreferenceInputAdapter(educationList, this, viewModel, "education", this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewEducation.setLayoutManager(layoutManager);
        binding.recyclerviewEducation.setAdapter(educationInputAdapter);
    }

    private void setDistanceRecylcerview() {
        // Get the string array from resources
        String[] distanceArray = getResources().getStringArray(R.array.distance_list);
        for (String distance : distanceArray) {
            PreferenceInput preferenceInput = new PreferenceInput(distance, false);
            distanceList.add(preferenceInput);
        }

        distanceInputAdapter = new PreferenceInputAdapter(distanceList, this, viewModel, "distance", this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerviewDistance.setLayoutManager(layoutManager);
        binding.recyclerviewDistance.setAdapter(distanceInputAdapter);
    }

    private void setGenderRecyclerview() {
        // Get the string array from resources
        String[] genderArray = getResources().getStringArray(R.array.gender_list);
        for (String gender : genderArray) {
            PreferenceInput preferenceInput = new PreferenceInput(gender, false);
            genderList.add(preferenceInput);
        }

        genderInputAdapter = new PreferenceInputAdapter(genderList, this, viewModel, "gender", this);
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
        viewModel.getProfilePreference();
        viewModel.preferenceResponse.observe(this, preferenceResponse -> {
            if (preferenceResponse != null) {
                Toast.makeText(this, "Preferred gender: " + preferenceResponse.getRace(), Toast.LENGTH_LONG).show();
                preferences = preferenceResponse;

                for (String education : preferences.getEducation()) {
                    if (initialStringEducation.equals(""))
                        initialStringEducation = initialStringEducation + education;
                    else
                        initialStringEducation = initialStringEducation + "," + education;
                }

                for (String religion : preferences.getReligion()) {
                    if (initialStringReligion.equals(""))
                        initialStringReligion = initialStringReligion + religion;
                    else
                        initialStringReligion = initialStringReligion + "," + religion;
                }
                setPreferences();

            }
        });
    }

    private void setPreferences() {
        for (PreferenceInput input : genderList)
            if (preferences.getInterestedIn().equalsIgnoreCase(input.getInputName())) {
                input.setSelected(true);
                genderInputAdapter.notifyDataSetChanged();
            }
        for (PreferenceInput input : distanceList)
            if (input.getInputName().toLowerCase().contains(String.valueOf(preferences.getDistance()))) {
                input.setSelected(true);
                distanceInputAdapter.notifyDataSetChanged();
            }
        for (PreferenceInput input : educationList) {
            String preference = String.join(", ", preferences.getEducation());

            if (preference.toLowerCase().contains(input.getInputName().toLowerCase())) {
                input.setSelected(true);
                educationInputAdapter.notifyDataSetChanged();
            }
        }
        for (PreferenceInput input : raceList) {
            String preference = String.join(", ", preferences.getRace());

            if (preference.toLowerCase().contains(input.getInputName().toLowerCase())) {
                input.setSelected(true);
                raceInputAdapter.notifyDataSetChanged();
            }
        }
        for (PreferenceInput input : religionList) {
            String preference = String.join(", ", preferences.getReligion());

            if (preference.toLowerCase().contains(input.getInputName().toLowerCase())) {
                input.setSelected(true);
                religionInputAdapter.notifyDataSetChanged();
            }
        }
        for (PreferenceInput input : cuisineList)
            if (preferences.getCuisine().contains(input.getInputName())) {
                input.setSelected(true);
                cuisineInputAdapter.notifyDataSetChanged();
            }
        for (PreferenceInput input : budgetList)
            if (preferences.getBudget().contains(input.getInputName().toLowerCase())) {
                input.setSelected(true);
                budgetInputAdapter.notifyDataSetChanged();
            }
        for (int i = 0; i < heightList.size(); i++)
            if (heightList.get(i).equals(convertCmToFeetInches(preferences.getMaxHeight())))
                binding.heightSpinnerMax.selectItemByIndex(i);
        for (int i = 0; i < heightList.size(); i++) {
            String minHeight = convertCmToFeetInches(preferences.getMinHeight());

            Toast.makeText(this, "min height selected:" + minHeight, Toast.LENGTH_SHORT).show();
            if (heightList.get(i).equals(minHeight))
                binding.heightSpinnerMin.selectItemByIndex(i);
        }
        for (int i = 0; i < ageList.size(); i++) {
            String minAgeSelected = preferences.getMinAge().toString() + " yr";
            if (ageList.get(i).equals(minAgeSelected)) {
                binding.ageSpinnerMin.selectItemByIndex(i);
            }
        }
        for (int i = 0; i < ageList.size(); i++) {
            String maxAgeSelected = preferences.getMaxAge().toString() + " yr";
            if (ageList.get(i).equals(maxAgeSelected)) {
                binding.ageSpinnerMax.selectItemByIndex(i);
            } else if (preferences.getMaxAge() >= 65)
                binding.ageSpinnerMax.selectItemByIndex(ageList.size() - 1);
        }
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
                ageList.add(i + " yr");
            else
                ageList.add(i + "+ yr");
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

    public static String convertCmToFeetInches(double centimeters) {
        double feet = centimeters / 30.48;
        int feetPart = (int) feet;
        double inches = (feet - feetPart) * 12;
        int inchesPart = (int) inches;

        return feetPart + "." + inchesPart + " ft";
    }

    @Override
    public void onSelect(boolean isSelected, String selectedInput, String selectedInputFor) {
        Log.d("PreferenceSettingsActivity", selectedInputFor + " selected = " + selectedInput + " :" + isSelected);
        switch (selectedInputFor) {
            case "gender":
                if (isSelected) {
                    for (PreferenceInput gender : genderList) {
                        if (gender.getInputName().equalsIgnoreCase(selectedInput)) {
                            gender.setSelected(false);
                            genderInputAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    for (PreferenceInput gender : genderList) {
                        if (gender.getInputName().equalsIgnoreCase(selectedInput)) {
                            gender.setSelected(true);
                            genderInputAdapter.notifyDataSetChanged();
                        } else
                            gender.setSelected(false);
                    }
                }
                updatedPreferences.setInterestedIn(selectedInput);
                Log.d("PreferenceSettingsActivity", "New preferred interested_in: " + updatedPreferences.getInterestedIn());
                break;
            case "distance":
                if (isSelected) {
                    for (PreferenceInput distance : distanceList) {
                        if (distance.getInputName().equalsIgnoreCase(selectedInput)) {
                            distance.setSelected(false);
                            distanceInputAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    for (PreferenceInput distance : distanceList) {
                        if (distance.getInputName().equalsIgnoreCase(selectedInput)) {
                            distance.setSelected(true);
                            distanceInputAdapter.notifyDataSetChanged();
                        } else
                            distance.setSelected(false);
                    }
                }
                String finalDistanceSelected = selectedInput.split(" ")[0];
                updatedPreferences.setDistance(Integer.valueOf(finalDistanceSelected));
                Log.d("PreferenceSettingsActivity", "New preferred distance: " + updatedPreferences.getDistance());
                break;
            case "education":
                Log.d("PreferenceSettingsActivity", selectedInputFor + " selected = " + selectedInput + " :" + isSelected);
                // If "Open to all" is selected, unselect all other items and set finalStringEducation
                if (selectedInput.equalsIgnoreCase("Open to all") && !isSelected) {
                    for (PreferenceInput education : educationList) {
                        if (education.getInputName().equalsIgnoreCase(selectedInput))
                            education.setSelected(true);
                        else
                            education.setSelected(false);
                    }
                    updatedPreferences.setEducation("Open to all");
                } else {
                    // Toggle the selection state of the clicked item
                    for (PreferenceInput education : educationList) {
                        if (education.getInputName().equalsIgnoreCase("Open to all"))
                            education.setSelected(false);
                        if (education.getInputName().equalsIgnoreCase(selectedInput)) {
                            education.setSelected(!isSelected);
                        }
                    }
                    // Iterate through the list to build the final string
                    String finalStringEducation = "";
                    for (PreferenceInput education : educationList) {
                        if (education.isSelected()) {
                            if (!finalStringEducation.isEmpty()) {
                                finalStringEducation += ", ";
                            }
                            finalStringEducation += education.getInputName();
                        }
                    }
                    if (!finalStringEducation.isEmpty())
                        updatedPreferences.setEducation(finalStringEducation);
                    else
                        updatedPreferences.setEducation(initialStringEducation);
                }
                Log.d("PreferenceSettingsActivity", "New preferred education: " + updatedPreferences.getEducation());
                educationInputAdapter.notifyDataSetChanged();
                break;
            case "religion":
                Log.d("PreferenceSettingsActivity", selectedInputFor + " selected = " + selectedInput + " :" + isSelected);
                // If "Open to all" is selected, unselect all other items and set finalStringEducation
                if (selectedInput.equalsIgnoreCase("Open to all") && !isSelected) {
                    for (PreferenceInput religion : religionList) {
                        if (religion.getInputName().equalsIgnoreCase(selectedInput))
                            religion.setSelected(true);
                        else
                            religion.setSelected(false);
                    }
                    updatedPreferences.setReligion("Open to all");
                } else {
                    // Toggle the selection state of the clicked item
                    for (PreferenceInput religion : religionList) {
                        if (religion.getInputName().equalsIgnoreCase("Open to all"))
                            religion.setSelected(false);
                        if (religion.getInputName().equalsIgnoreCase(selectedInput)) {
                            religion.setSelected(!isSelected);
                        }
                    }
                    // Iterate through the list to build the final string
                    String finalStringReligion = "";
                    for (PreferenceInput religion : religionList) {
                        if (religion.isSelected()) {
                            if (!finalStringReligion.isEmpty()) {
                                finalStringReligion += ", ";
                            }
                            finalStringReligion += religion.getInputName();
                        }
                    }
                    if (!finalStringReligion.isEmpty())
                        updatedPreferences.setReligion(finalStringReligion);
                    else
                        updatedPreferences.setReligion(initialStringReligion);
                }
                Log.d("PreferenceSettingsActivity", "New preferred religion: " + updatedPreferences.getReligion());
                religionInputAdapter.notifyDataSetChanged();
                break;
        }
    }
}