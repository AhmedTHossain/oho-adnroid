package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;
import com.shawnlin.numberpicker.NumberPicker;

public class HeightInputFragment extends Fragment {

    private NumberPicker feetPicker, inchPicker;

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();
    private double heightInput = 0.0;
    private int feetInput = 0;
    private int inchInput = 0;

    public HeightInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_height_input, container, false);

        feetPicker = view.findViewById(R.id.number_picker_feet);
        inchPicker = view.findViewById(R.id.number_picker_inch);

        feetPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                feetInput = newVal;
                Log.d("HeightInputFragment","feet = "+feetInput);
            }
        });

        inchPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                inchInput = newVal;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        viewModel.getRegistrationFormData().observe(getViewLifecycleOwner(), new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                profileData = profile;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        heightInput = convertHeightToCm(feetInput,inchInput);
        Log.d("HeightInputFragment","height final = " + heightInput);

        if (heightInput == 0.0){
            Toast.makeText(requireContext(), "Please enter your height first!", Toast.LENGTH_SHORT).show();
        }else {
            profileData.setHeight(heightInput);
            viewModel.saveRegistrationFormData(profileData);
            Log.d("HeightInputFragment","onPause height = " + profileData.getHeight());
        }
    }

    //function to convert height input to centimeters
    private double convertHeightToCm(int ftInput, int inInput){
        return ((ftInput * 12) + inInput) * 2.54;
    }
}