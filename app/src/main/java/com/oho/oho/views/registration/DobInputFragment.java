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

import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Arrays;

public class DobInputFragment extends Fragment {

    private NumberPicker monthPicker, dayPicker, yearPicker;
    private String monthInput, dayInput, yearInput, dobInput = "";

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();

    private final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public DobInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dob_input, container, false);

        dayPicker = view.findViewById(R.id.number_picker_day);
        monthPicker = view.findViewById(R.id.number_picker_month);
        yearPicker = view.findViewById(R.id.number_picker_year);

        monthPicker.setDisplayedValues(months);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setDisplayedValues(months);
        monthPicker.setValue(1);

        Log.d("DobInputFragment","month final = "+monthPicker.getValue());

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                monthInput = String.valueOf(newVal);
            }
        });

        dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dayInput = String.valueOf(newVal);
            }
        });

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearInput = String.valueOf(newVal);
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

        dobInput = monthInput + "/" + dayInput + "/" + yearInput;
        Log.d("DobInputFragment","dob = "+dobInput);

        if (dobInput.equals("")){
            Toast.makeText(requireContext(), "Please enter your birth date first!", Toast.LENGTH_SHORT).show();
        } else {
            profileData.setDob(dobInput);
            viewModel.saveRegistrationFormData(profileData);
        }
    }
}