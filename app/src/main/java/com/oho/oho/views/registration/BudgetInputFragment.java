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

public class BudgetInputFragment extends Fragment implements View.OnClickListener{

    private CardView buttonOneDollar, buttonTwoDollar, buttonThreeDollar;
    private TextView buttonOneDollarText, buttonTwoDollarText, buttonThreeDollarText;
    private String budgetInput = "";
    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();

    public BudgetInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget_input, container, false);


        buttonOneDollar = view.findViewById(R.id.button_one_dollar);
        buttonTwoDollar = view.findViewById(R.id.button_two_dollar);
        buttonThreeDollar = view.findViewById(R.id.button_three_dollar);
        buttonOneDollarText = view.findViewById(R.id.text_one_dollar);
        buttonTwoDollarText = view.findViewById(R.id.text_two_dollar);
        buttonThreeDollarText = view.findViewById(R.id.text_three_dollar);

        buttonOneDollar.setOnClickListener(this);
        buttonTwoDollar.setOnClickListener(this);
        buttonThreeDollar.setOnClickListener(this);

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
//                budgetInput = profile.getBudget()();
                if (profile.getBudget() != null){
                    switch (profile.getBudget()){
                        case "$":
                            buttonOneDollarText.setBackgroundResource(R.drawable.input_selected_background);
                            buttonOneDollarText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                        case "$$":
                            buttonTwoDollarText.setBackgroundResource(R.drawable.input_selected_background);
                            buttonTwoDollarText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                        case "$$$":
                            buttonThreeDollarText.setBackgroundResource(R.drawable.input_selected_background);
                            buttonThreeDollarText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!budgetInput.equals("")) {
            profileData.setBudget(budgetInput);
            viewModel.saveRegistrationFormData(profileData);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_one_dollar){
            if(budgetInput.equals("")){
                buttonOneDollarText.setBackgroundResource(R.drawable.input_selected_background);
                buttonOneDollarText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                budgetInput = "$";
            } else {
                if (budgetInput.equals("$")){
                    buttonOneDollarText.setBackgroundResource(R.color.white);
                    buttonOneDollarText.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    budgetInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();

            }
        }
        if(v.getId() == R.id.button_two_dollar){
            if(budgetInput.equals("")){
                buttonTwoDollarText.setBackgroundResource(R.drawable.input_selected_background);
                buttonTwoDollarText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                budgetInput = "$$";
            } else {
                if (budgetInput.equals("$$")){
                    buttonTwoDollarText.setBackgroundResource(R.color.white);
                    buttonTwoDollarText.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    budgetInput = "";
                }else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();

            }
        }
        if(v.getId() == R.id.button_three_dollar){
            if(budgetInput.equals("")){
                buttonThreeDollarText.setBackgroundResource(R.drawable.input_selected_background);
                buttonThreeDollarText.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
                budgetInput = "$$$";
            } else {
                if (budgetInput.equals("$$$")){
                    buttonThreeDollarText.setBackgroundResource(R.color.white);
                    buttonThreeDollarText.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
                    budgetInput = "";
                } else
                    Toast.makeText(getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}