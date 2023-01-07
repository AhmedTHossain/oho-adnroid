package com.oho.oho.views.settings;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oho.oho.R;
import com.oho.oho.databinding.FragmentPreferenceSettingsBinding;
import com.oho.oho.responses.PreferenceResponse;
import com.oho.oho.viewmodels.PreferenceSettingsViewModel;
import com.oho.oho.views.registration.RegistrationActivity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;

public class PreferenceSettingsFragment extends Fragment {

    private FragmentPreferenceSettingsBinding binding;
    private PreferenceSettingsViewModel viewModel;
    private ArrayList<String> ageList = new ArrayList();
    private ArrayList<String> heightList = new ArrayList();
    private ArrayList<String> cuisineList = new ArrayList<>();


    //preference attributes
    String prefGender, prefEducation, prefRace, prefReligion, prefCuisine;
    private int prefDistance;

    public PreferenceSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPreferenceSettingsBinding.inflate(inflater, container, false);

        initViewModel();
        getPreferences();

        setHeightSpinner();
        setAgeSpinner();

        binding.buttonGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrentLocation();
            }
        });

        binding.buttonGroupGender.setOnSelectListener((ThemedButton btn) -> {
            // handle selected button
            prefGender = btn.getText();
            return kotlin.Unit.INSTANCE;
        });

        binding.buttonGroupDistance.setOnSelectListener((ThemedButton btn) -> {
            // handle selected button
            prefDistance = Integer.parseInt(btn.getText().split(" ")[0]);
            return kotlin.Unit.INSTANCE;
        });

        binding.buttonGroupEducation.setOnSelectListener((ThemedButton btn) -> {
            // handle selected button
            prefEducation = btn.getText();
            return kotlin.Unit.INSTANCE;
        });

        binding.buttonGroupReligion.setOnSelectListener((ThemedButton btn) -> {
            // handle selected button
            prefReligion = btn.getText();
            return kotlin.Unit.INSTANCE;
        });

        binding.buttonGroupCuisine.setOnSelectListener((ThemedButton btn) -> {
            // handle selected button
            if (btn.isSelected()) {
                if (prefCuisine != null)
                    prefCuisine = prefCuisine.replace(btn.getText(), "");
            } else {
                if (prefCuisine != null && !prefCuisine.equals(""))
                    prefCuisine = prefCuisine + ", " + btn.getText();
                else
                    prefCuisine = btn.getText();
            }
            return kotlin.Unit.INSTANCE;
        });

        binding.updatePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePreference();
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PreferenceSettingsViewModel.class);
    }

    private void getPreferences() {
        getLocation();
        //TODO: later in place of hard coded id send logged in user's id
        viewModel.getProfilePreference("18");
        viewModel.preferenceResponse.observe(getViewLifecycleOwner(), preferenceResponse -> {
            if (preferenceResponse != null) {
                setPreferences(preferenceResponse);
            }
        });
    }

    private void getLocation() {
        viewModel.getStoredUserLocation(18);
        viewModel.userLocation.observe(getViewLifecycleOwner(), location -> {
            binding.textLocationPreference.setText(location);
        });
    }

    private void setHeightSpinner() {
        for (int i = 4; i < 9; i++) {
            for (int j = 0; j < 12; j++)
                heightList.add(i + "." + j + " ft");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, heightList);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, ageList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.ageSpinnerMin.setAdapter(adapter);
        binding.ageSpinnerMax.setAdapter(adapter);

        binding.ageSpinnerMin.setSelection(5);
        binding.ageSpinnerMax.setSelection(10);
    }

    private void setPreferences(PreferenceResponse preferences) {
        int indexMinAge = ageList.indexOf(preferences.getMinAge() + " years");
        int indexMaxAge = ageList.indexOf(preferences.getMaxAge() + " years");

        double minHeight = convertHeight(preferences.getMinHeight());
        double maxHeight = convertHeight(preferences.getMaxHeight());

        int indexMinHeight = heightList.indexOf(minHeight + " ft");
        int indexMaxHeight = heightList.indexOf(maxHeight + " ft");

        binding.ageSpinnerMin.setSelection(indexMinAge);
        binding.ageSpinnerMax.setSelection(indexMaxAge);
        binding.heightSpinnerMin.setSelection(indexMinHeight);
        binding.heightSpinnerMax.setSelection(indexMaxHeight);

        switch (preferences.getInterestedIn()) {
            case "Male":
                binding.buttonGroupGender.selectButton(R.id.button_male);
                break;
            case "Female":
                binding.buttonGroupGender.selectButton(R.id.button_female);
                break;
            default:
                binding.buttonGroupGender.selectButton(R.id.button_both);
                break;
        }
        switch (preferences.getDistance()) {
            case 10:
                binding.buttonGroupDistance.selectButton(R.id.button_10_miles);
                break;
            case 20:
                binding.buttonGroupDistance.selectButton(R.id.button_20_miles);
                break;
            case 30:
                binding.buttonGroupDistance.selectButton(R.id.button_30_miles);
                break;
            case 40:
                binding.buttonGroupDistance.selectButton(R.id.button_40_miles);
                break;
            case 50:
                binding.buttonGroupDistance.selectButton(R.id.button_50_miles);
                break;
            default:
                binding.buttonGroupDistance.selectButton(R.id.button_10_miles);
                break;
        }
        switch (preferences.getEducation()) {
            case "No Degree":
                binding.buttonGroupEducation.selectButton(R.id.button_no_degree);
                break;
            case "High School":
                binding.buttonGroupEducation.selectButton(R.id.button_highschool);
                break;
            case "Undergrad":
                binding.buttonGroupEducation.selectButton(R.id.button_undergrad);
                break;
            case "Masters":
                binding.buttonGroupEducation.selectButton(R.id.button_masters);
                break;
            case "PHD":
                binding.buttonGroupEducation.selectButton(R.id.button_phd);
                break;
        }
        switch (preferences.getRace()) {
            case "White":
                binding.buttonGroupRace.selectButton(R.id.button_white);
                break;
            case "Asian":
                binding.buttonGroupRace.selectButton(R.id.button_asian);
                break;
            case "Black":
                binding.buttonGroupRace.selectButton(R.id.button_black);
                break;
            case "African American":
                binding.buttonGroupRace.selectButton(R.id.button_africanamerican);
                break;
            case "Hispanic":
                binding.buttonGroupRace.selectButton(R.id.button_hispanic);
                break;
            case "Latinx":
                binding.buttonGroupRace.selectButton(R.id.button_latinx);
                break;
            case "Pacific Islander":
                binding.buttonGroupRace.selectButton(R.id.button_pacificislander);
                break;
            case "American Indian":
                binding.buttonGroupRace.selectButton(R.id.button_americanindian);
                break;
            default:
                binding.buttonGroupRace.selectButton(R.id.button_others);
                break;
        }
        switch (preferences.getReligion()) {
            case "Christian":
                binding.buttonGroupReligion.selectButton(R.id.button_christian);
                break;
            case "Catholic":
                binding.buttonGroupReligion.selectButton(R.id.button_catholic);
                break;
            case "Jewish":
                binding.buttonGroupReligion.selectButton(R.id.button_jewish);
                break;
            case "Muslim":
                binding.buttonGroupReligion.selectButton(R.id.button_muslim);
                break;
            case "Hindu":
                binding.buttonGroupReligion.selectButton(R.id.button_hindu);
                break;
            case "Sikh":
                binding.buttonGroupReligion.selectButton(R.id.button_sikh);
                break;
            case "Agnostic":
                binding.buttonGroupReligion.selectButton(R.id.button_agnostic);
                break;
            case "Buddhist":
                binding.buttonGroupReligion.selectButton(R.id.button_buddhist);
                break;
            case "Spiritual/Not Religious":
                binding.buttonGroupReligion.selectButton(R.id.button_spiritualnotreligious);
                break;
            case "Atheist":
                binding.buttonGroupReligion.selectButton(R.id.button_atheist);
                break;
            case "Other":
                binding.buttonGroupReligion.selectButton(R.id.button_other);
                break;
        }

//        String [] cuisines = preferences.getCuisine();
        cuisineList.addAll(preferences.getCuisine());

        for (String cuisine : cuisineList) {
            if (cuisine.equals("Italian"))
                binding.buttonGroupCuisine.selectButton(R.id.button_italian);
            if (cuisine.equals("Japanese"))
                binding.buttonGroupCuisine.selectButton(R.id.button_japanese);
            if (cuisine.equals("Indian"))
                binding.buttonGroupCuisine.selectButton(R.id.button_indian);
            if (cuisine.equals("Mediterranean"))
                binding.buttonGroupCuisine.selectButton(R.id.button_mediterranean);
            if (cuisine.equals("Korean"))
                binding.buttonGroupCuisine.selectButton(R.id.button_korean);
            if (cuisine.equals("American"))
                binding.buttonGroupCuisine.selectButton(R.id.button_american);
            if (cuisine.equals("Vegetarian"))
                binding.buttonGroupCuisine.selectButton(R.id.button_vegetarian);
            if (cuisine.equals("Vietnamese"))
                binding.buttonGroupCuisine.selectButton(R.id.button_vietnamese);
            if (cuisine.equals("Chinese"))
                binding.buttonGroupCuisine.selectButton(R.id.button_chinese);
            if (cuisine.equals("Others"))
                binding.buttonGroupCuisine.selectButton(R.id.button_others);
            if (cuisine.equals("Open to all"))
                binding.buttonGroupCuisine.selectButton(R.id.button_opentoall);

        }
    }

    private void showCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {

                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        try {
                            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );

                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();

                            String locationText = city + ", " + state;
                            binding.textLocationPreference.setText(locationText);

                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            binding.textLocationPreference.setText("Tap again to set your location");
            //when permission denied
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private double convertHeight(double height) {
        double heightInFeet = height * 0.0328;

        Log.d("PreferenceSettings", "height returned: " + heightInFeet);
        DecimalFormat df = new DecimalFormat("0.0");
        double heightInFeetRounded = Double.parseDouble(df.format(heightInFeet));
        Log.d("PreferenceSettings", "height returned rounded: " + heightInFeetRounded);

        return heightInFeetRounded;
    }

    private String[] getPreferredCuisines(String cuisines) {
        return cuisines.split(",");
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferences();
    }

    private void updatePreference() {
        binding.updatePreferencesProgress.setVisibility(View.VISIBLE);
        PreferenceResponse preference = new PreferenceResponse();
        preference.setUserId(18); //TODO: hard coded user id will be replaced with the logged in user's id
        if (prefGender != null)
            preference.setInterestedIn(prefGender);
        if (prefDistance != 0)
            preference.setDistance(prefDistance);
        if (prefEducation != null)
            preference.setEducation(prefEducation);
        if (prefReligion != null)
            preference.setReligion(prefReligion);
//        if (prefCuisine != null)
//            preference.setCuisine(prefCuisine);
        viewModel.updatePreference(preference);
        viewModel.ifPreferenceUpdated.observe(getViewLifecycleOwner(), ifPreferenceUpdated -> {
            binding.updatePreferencesProgress.setVisibility(View.GONE);
            Toast.makeText(requireContext(), "Your preferences updated successfully!", Toast.LENGTH_SHORT).show();
        });
    }
}