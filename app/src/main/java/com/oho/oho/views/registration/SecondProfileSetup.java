package com.oho.oho.views.registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentSecondProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.ProfileSetupViewModel;
import com.shawnlin.numberpicker.NumberPicker;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;

public class SecondProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentSecondProfileSetupBinding binding;
    private ProfileSetupViewModel viewmodel;
    private double height = 0.0;
    private String education = "";
    private String heightToDisplay = "";

    public SecondProfileSetup() {
        // Required empty public constructor
    }

    public SecondProfileSetup(OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondProfileSetupBinding.inflate(inflater, container, false);

        binding.buttonNextSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!education.equals("")) {
                    if (!heightToDisplay.equals("")) {
                        viewmodel = new ViewModelProvider(requireActivity()).get(ProfileSetupViewModel.class);

                        Profile profile = viewmodel.getNewUserProfile().getValue();
                        profile.setEducation(education);
                        profile.setHeight(height);

                        viewmodel.updateNewUserProfile(profile);
                        viewmodel.newUserProfile.observe(requireActivity(), newUserProfile -> {
                            Log.d("SecondProfileSetup", "education stored in viewmodel: " + newUserProfile.getEducation());
                        });
                        listener.onScreenChange("next", "second");
                    } else
                        Toast.makeText(requireContext(), "Please select your Height first!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(requireContext(), "Please select your Degree first!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.textviewHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHeightInputDialog();
            }
        });

        binding.buttonGroupEducation.setOnSelectListener((ThemedButton btn) -> {
            education = btn.getText();
            // handle selected button
            return kotlin.Unit.INSTANCE;
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void showHeightInputDialog() {
        LayoutInflater li = LayoutInflater.from(requireContext());
        View promptsView = li.inflate(R.layout.height_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final com.shawnlin.numberpicker.NumberPicker feetPicker = promptsView.findViewById(R.id.number_picker_feet);
        final NumberPicker inchPicker = promptsView.findViewById(R.id.number_picker_inch);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Select", null);
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.indicatioractive));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        height = calculateHeight(feetPicker.getValue(), inchPicker.getValue());
                        heightToDisplay = feetPicker.getValue() + "' " + inchPicker.getValue() + "\"";
                        binding.textviewHeight.setText(heightToDisplay);
                        dialog.dismiss();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }

    public double calculateHeight(int feet, int inch) {
        int totalInch = (feet * 12) + inch;

        return totalInch * 2.54;
    }

}