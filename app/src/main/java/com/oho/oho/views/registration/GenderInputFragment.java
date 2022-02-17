package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;

import java.util.Objects;

public class GenderInputFragment extends Fragment implements View.OnClickListener{

    private CardView buttonMale, buttonFemale, buttonOther;
    private TextView buttonMaleText, buttonFemaleText, buttonOtherText;
    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();

    private String genderInput = "";

    public GenderInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gender_input, container, false);

        buttonMale = view.findViewById(R.id.button_male);
        buttonFemale = view.findViewById(R.id.button_female);
        buttonOther = view.findViewById(R.id.button_other);

        buttonMaleText = view.findViewById(R.id.text_male);
        buttonFemaleText = view.findViewById(R.id.text_female);
        buttonOtherText = view.findViewById(R.id.text_other);

        buttonMale.setOnClickListener(this);
        buttonFemale.setOnClickListener(this);
        buttonOther.setOnClickListener(this);

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
        if (!genderInput.equals("")) {
            profileData.setGender(genderInput);
            viewModel.saveRegistrationFormData(profileData);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_male){
            if(genderInput.equals("")){
                buttonMaleText.setBackgroundResource(R.drawable.input_selected_background);
                buttonMaleText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                genderInput = "Male";
            } else {
                if (genderInput.equals("Male")){
                    buttonMaleText.setBackgroundResource(R.color.white);
                    buttonMaleText.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    genderInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();

            }
        }
        if(v.getId() == R.id.button_female){
            if(genderInput.equals("")){
                buttonFemaleText.setBackgroundResource(R.drawable.input_selected_background);
                buttonFemaleText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                genderInput = "Female";
            } else {
                if (genderInput.equals("Female")){
                    buttonFemaleText.setBackgroundResource(R.color.white);
                    buttonFemaleText.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    genderInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();

            }
        }
        if(v.getId() == R.id.button_other){
            if(genderInput.equals("")){
                buttonOtherText.setBackgroundResource(R.drawable.input_selected_background);
                buttonOtherText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                genderInput = "Other";
            } else {
                if (genderInput.equals("Other")){
                    buttonOtherText.setBackgroundResource(R.color.white);
                    buttonOtherText.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    genderInput = "";
                } else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}