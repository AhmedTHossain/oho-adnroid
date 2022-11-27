package com.oho.oho.views.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentPreferenceSettingsBinding;
import com.oho.oho.responses.PreferenceResponse;
import com.oho.oho.viewmodels.PreferenceSettingsViewModel;

import java.util.ArrayList;

public class PreferenceSettingsFragment extends Fragment {

    private FragmentPreferenceSettingsBinding binding;
    private PreferenceSettingsViewModel viewModel;

    public PreferenceSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPreferenceSettingsBinding.inflate(inflater, container, false);

        initViewModel();
//        getPreferences();

        setHeightSpinner();
        setAgeSpinner();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PreferenceSettingsViewModel.class);
    }

    private void getPreferences() {
        //TODO: later in place of hard coded id send logged in user's id
        viewModel.getProfilePreference("18");
        viewModel.preferenceResponse.observe(getViewLifecycleOwner(), preferenceResponse -> {
            if (preferenceResponse != null) {
                setPreferences(preferenceResponse);
            }
        });
    }

    private void setHeightSpinner() {

        ArrayList<String> heightList = new ArrayList();

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
        ArrayList<String> ageList = new ArrayList();

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
        switch (preferences.getInterestedIn()) {
            case "Male":
                binding.groupInterestedIn.check(0);
                break;
            case "Female":
                binding.groupInterestedIn.check(1);
                break;
            default:
                binding.groupInterestedIn.check(2);
                break;
        }
        binding.distanceText.setText(preferences.getDistance());
        switch (preferences.getEducation()) {
            case "No Degree":
                binding.groupEducation.check(0);
                break;
            case "High School":
                binding.groupEducation.check(1);
                break;
            case "Undergrad":
                binding.groupEducation.check(2);
                break;
            case "Masters":
                binding.groupEducation.check(3);
                break;
            case "PHD":
                binding.groupEducation.check(4);
        }
    }
}