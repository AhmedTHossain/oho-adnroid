package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;
import com.shawnlin.numberpicker.NumberPicker;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

public class DobInputFragment extends Fragment {

    private NumberPicker monthPicker, dayPicker, yearPicker;
    private CardView ageCard;
    private TextView ageTextView;
    private String monthInput = "", dayInput = "", yearInput = "", dobInput = "";

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

        dayPicker   = view.findViewById(R.id.number_picker_day);
        monthPicker = view.findViewById(R.id.number_picker_month);
        yearPicker  = view.findViewById(R.id.number_picker_year);
        ageCard     = view.findViewById(R.id.card_age_layout);
        ageTextView = view.findViewById(R.id.text_age);

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

                if (dayInput.equals(""))
                    dayInput = String.valueOf(dayPicker.getValue());
                if (yearInput.equals(""))
                    yearInput = String.valueOf(yearPicker.getValue());

                int age = getAge(Integer.parseInt(yearInput),Integer.parseInt(monthInput),Integer.parseInt(dayInput));

                String ageText = age + " years";
                ageTextView.setText(ageText);
                ageCard.setVisibility(View.VISIBLE);
            }
        });

        dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dayInput = String.valueOf(newVal);

                if (monthInput.equals(""))
                    monthInput = String.valueOf(monthPicker.getValue());
                if (yearInput.equals(""))
                    yearInput = String.valueOf(yearPicker.getValue());

                int age = getAge(Integer.parseInt(yearInput),Integer.parseInt(monthInput),Integer.parseInt(dayInput));

                String ageText = age + " years";
                ageTextView.setText(ageText);
                ageCard.setVisibility(View.VISIBLE);
            }
        });

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearInput = String.valueOf(newVal);

                if (dayInput.equals(""))
                    dayInput = String.valueOf(dayPicker.getValue());
                if (monthInput.equals(""))
                    monthInput = String.valueOf(monthPicker.getValue());

                int age = getAge(Integer.parseInt(yearInput),Integer.parseInt(monthInput),Integer.parseInt(dayInput));

                String ageText = age + " years";
                ageTextView.setText(ageText);
                ageCard.setVisibility(View.VISIBLE);
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
                dobInput = profile.getDob();

                if(dobInput != null) {
                    String[] dobArray = dobInput.split("/");

                    dayPicker.setValue(Integer.parseInt(dobArray[1]));
                    yearPicker.setValue(Integer.parseInt(dobArray[dobArray.length - 1]));

                    String ageText = profile.getAge() + " years";
                    ageTextView.setText(ageText);
                    ageCard.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        int age = 0;

        if (dayInput.equals(""))
            dayInput = String.valueOf(dayPicker.getValue());
        if (monthInput.equals(""))
            monthInput = String.valueOf(monthPicker.getValue());
        if (yearInput.equals(""))
            yearInput = String.valueOf(yearPicker.getValue());

        dobInput = monthInput + "/" + dayInput + "/" + yearInput;
        age = getAge(Integer.parseInt(yearInput),Integer.parseInt(monthInput),Integer.parseInt(dayInput));

        profileData.setDob(dobInput);
        viewModel.saveRegistrationFormData(profileData);
        profileData.setAge(age);

        Log.d("DobInputFragment","onPause date of birth = " + profileData.getDob());
        Log.d("DobInputFragment","onPause age = " + profileData.getAge());
    }

    public int getAge(int year, int month, int dayOfMonth) {
        return Period.between(
                LocalDate.of(year, month, dayOfMonth),
                LocalDate.now()
        ).getYears();
    }
}