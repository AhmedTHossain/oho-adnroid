package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.adapters.RegistrationInputFieldAdapter;
import com.oho.oho.models.Profile;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.viewmodels.RegistrationViewModel;
import com.oho.oho.views.listeners.OnInputSelectListener;

import java.util.ArrayList;

public class EducationInputFragment extends Fragment implements View.OnClickListener {

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();
    private String educationInput="";

    private TextView textNoDegree, textHighSchool, textUndergrad, textMasters, textPhd;

    public EducationInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education_input, container, false);

        textNoDegree = view.findViewById(R.id.text_no_degree);
        textHighSchool = view.findViewById(R.id.text_high_school_diploma);
        textUndergrad = view.findViewById(R.id.text_undergrad);
        textMasters = view.findViewById(R.id.text_masters);
        textPhd = view.findViewById(R.id.text_phd);

        textNoDegree.setOnClickListener(this);
        textHighSchool.setOnClickListener(this);
        textUndergrad.setOnClickListener(this);
        textMasters.setOnClickListener(this);
        textPhd.setOnClickListener(this);

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
                if (profileData.getEducation() != null) {
                    educationInput = profileData.getEducation();
                    switch (educationInput){
                        case "No Degree":
                            textNoDegree.setBackgroundResource(R.drawable.input_selected_background);
                            textNoDegree.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                        case "High School Diploma":
                            textHighSchool.setBackgroundResource(R.drawable.input_selected_background);
                            textHighSchool.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                        case "Undergrad":
                            textUndergrad.setBackgroundResource(R.drawable.input_selected_background);
                            textUndergrad.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                        case "Masters":
                            textMasters.setBackgroundResource(R.drawable.input_selected_background);
                            textMasters.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                        case "PhD":
                            textPhd.setBackgroundResource(R.drawable.input_selected_background);
                            textPhd.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!educationInput.equals("")) {
            profileData.setEducation(educationInput);
            viewModel.saveRegistrationFormData(profileData);
        } else {
            profileData.setEducation("");
            viewModel.saveRegistrationFormData(profileData);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.text_no_degree){
            if(educationInput.equals("")){
                textNoDegree.setBackgroundResource(R.drawable.input_selected_background);
                textNoDegree.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                educationInput = textNoDegree.getText().toString();
            } else {
                if (educationInput.equals(textNoDegree.getText().toString())){
                    textNoDegree.setBackgroundResource(R.color.white);
                    textNoDegree.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    educationInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.text_high_school_diploma){
            if(educationInput.equals("")){
                textHighSchool.setBackgroundResource(R.drawable.input_selected_background);
                textHighSchool.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                educationInput = textHighSchool.getText().toString();
            } else {
                if (educationInput.equals(textHighSchool.getText().toString())){
                    textHighSchool.setBackgroundResource(R.color.white);
                    textHighSchool.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    educationInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.text_undergrad){
            if(educationInput.equals("")){
                textUndergrad.setBackgroundResource(R.drawable.input_selected_background);
                textUndergrad.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                educationInput = textUndergrad.getText().toString();
            } else {
                if (educationInput.equals(textUndergrad.getText().toString())){
                    textUndergrad.setBackgroundResource(R.color.white);
                    textUndergrad.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    educationInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.text_masters){
            if(educationInput.equals("")){
                textMasters.setBackgroundResource(R.drawable.input_selected_background);
                textMasters.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                educationInput = textMasters.getText().toString();
            } else {
                if (educationInput.equals(textMasters.getText().toString())){
                    textMasters.setBackgroundResource(R.color.white);
                    textMasters.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    educationInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.text_phd){
            if(educationInput.equals("")){
                textPhd.setBackgroundResource(R.drawable.input_selected_background);
                textPhd.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                educationInput = textPhd.getText().toString();
            } else {
                if (educationInput.equals(textPhd.getText().toString())){
                    textPhd.setBackgroundResource(R.color.white);
                    textPhd.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    educationInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}