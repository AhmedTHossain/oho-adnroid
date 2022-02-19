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

public class VaccineInputFragment extends Fragment implements View.OnClickListener{

    private CardView buttonYes, buttonNo;
    private TextView buttonYesText, buttonNoText;

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();

    private String vaccineInput = "";

    public VaccineInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccine_input, container, false);

        buttonYes = view.findViewById(R.id.button_yes);
        buttonNo = view.findViewById(R.id.button_no);
        buttonYesText = view.findViewById(R.id.text_yes);
        buttonNoText = view.findViewById(R.id.text_no);

        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);

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
                if (profileData.getVaccinated() != null){
                    switch (profileData.getVaccinated()){
                        case "Yes":
                            buttonYesText.setBackgroundResource(R.drawable.input_selected_background);
                            buttonYesText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                        case "No":
                            buttonNoText.setBackgroundResource(R.drawable.input_selected_background);
                            buttonNoText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                    }
                }
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        if (!vaccineInput.equals("")) {
            profileData.setVaccinated(vaccineInput);
            viewModel.saveRegistrationFormData(profileData);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_yes){
            if(vaccineInput.equals("")){
                buttonYesText.setBackgroundResource(R.drawable.input_selected_background);
                buttonYesText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                vaccineInput = "Yes";
            } else {
                if (vaccineInput.equals("Yes")){
                    buttonYesText.setBackgroundResource(R.color.white);
                    buttonYesText.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    vaccineInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();

            }
        }
        if(v.getId() == R.id.button_no){
            if(vaccineInput.equals("")){
                buttonNoText.setBackgroundResource(R.drawable.input_selected_background);
                buttonNoText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                vaccineInput = "No";
            } else {
                if (vaccineInput.equals("No")){
                    buttonNoText.setBackgroundResource(R.color.white);
                    buttonNoText.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    vaccineInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();

            }
        }
    }
}