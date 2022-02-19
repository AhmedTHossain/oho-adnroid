package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;

public class GenderPreferenceInputFragment extends Fragment implements View.OnClickListener{

    private CardView buttonMale, buttonFemale, buttonOpenToAll;
    private TextView buttonMaleText, buttonFemaleText, buttonOpenToAllText;
    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();

    private String genderInput = "";

    public GenderPreferenceInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gender_preference_input, container, false);

        buttonMale = view.findViewById(R.id.button_male);
        buttonFemale = view.findViewById(R.id.button_female);
        buttonOpenToAll = view.findViewById(R.id.button_open_to_all);

        buttonMaleText = view.findViewById(R.id.text_male);
        buttonFemaleText = view.findViewById(R.id.text_female);
        buttonOpenToAllText = view.findViewById(R.id.text_open_to_all);

        buttonMale.setOnClickListener(this);
        buttonFemale.setOnClickListener(this);
        buttonOpenToAll.setOnClickListener(this);

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
                if (profileData.getInterested_in() != null) {
                    genderInput = profileData.getInterested_in();
                    switch (profileData.getInterested_in()) {
                        case "Male":
                            buttonMaleText.setBackgroundResource(R.drawable.input_selected_background);
                            buttonMaleText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                        case "Female":
                            buttonFemaleText.setBackgroundResource(R.drawable.input_selected_background);
                            buttonFemaleText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                        case "Open to all":
                            buttonOpenToAllText.setBackgroundResource(R.drawable.input_selected_background);
                            buttonOpenToAllText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!genderInput.equals("")) {
            profileData.setInterested_in(genderInput);
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
        if(v.getId() == R.id.button_open_to_all){
            if(genderInput.equals("")){
                buttonOpenToAllText.setBackgroundResource(R.drawable.input_selected_background);
                buttonOpenToAllText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                genderInput = "Open to all";
            } else {
                if (genderInput.equals("Open to all")){
                    buttonOpenToAllText.setBackgroundResource(R.color.white);
                    buttonOpenToAllText.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    genderInput = "";
                } else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}