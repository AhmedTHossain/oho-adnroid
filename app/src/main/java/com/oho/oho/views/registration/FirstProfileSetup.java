package com.oho.oho.views.registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentFirstProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.models.Profile;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.viewmodels.ProfileSetupViewModel;

import java.util.Calendar;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;


public class FirstProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentFirstProfileSetupBinding binding;
    private ProfileSetupViewModel viewmodel;
    private int age;
    private String gender = "";
    private String dob = "";

    public FirstProfileSetup() {
        // Required empty public constructor
    }

    public FirstProfileSetup(OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstProfileSetupBinding.inflate(inflater, container, false);

        binding.buttonNextIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gender.equals("")) {
                    if (!dob.equals("")) {
                        if (age >= 18) {
                            viewmodel = new ViewModelProvider(requireActivity()).get(ProfileSetupViewModel.class);

                            Profile profile = new Profile();

                            profile.setName(requireActivity().getIntent().getStringExtra("name"));
                            profile.setEmail(requireActivity().getIntent().getStringExtra("email"));
                            profile.setSex(gender);
                            profile.setAge(age);
                            profile.setDob(binding.textviewDateOfBirth.getText().toString());

                            viewmodel.updateNewUserProfile(profile);
                            viewmodel.newUserProfile.observe(requireActivity(), newUserProfile -> {
                                Log.d("FirstProfileSetup", "age stored in viewmodel: " + newUserProfile.getAge());
                            });

                            listener.onScreenChange("next", "first");
                        } else
                            new HelperClass().showSnackBar(binding.containermain,"Sorry! we only provide our service to users who are 18 or above");
//                            Toast.makeText(requireContext(), "Sorry we only provide our service to users who are 18 or above", Toast.LENGTH_SHORT).show();
                    } else
                        new HelperClass().showSnackBar(binding.containermain,"Please enter your Date of Birth first!");
//                        Toast.makeText(requireContext(), "Please enter your Date of Birth first!", Toast.LENGTH_SHORT).show();
                } else
                    new HelperClass().showSnackBar(binding.containermain,"Please select your Gender first!");
//                    Toast.makeText(requireContext(), "Please select your Gender first!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.textviewDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateInputDialog();
            }
        });

        binding.buttonGroupGender.setOnSelectListener((ThemedButton btn) -> {
            gender = btn.getText();
            // handle selected button
            return kotlin.Unit.INSTANCE;
        });

        return binding.getRoot();
    }

    private void showDateInputDialog() {
        LayoutInflater li = LayoutInflater.from(requireContext());
        View promptsView = li.inflate(R.layout.date_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final DatePicker datePicker = promptsView.findViewById(R.id.datepicker);
        alertDialogBuilder.setCancelable(true).setPositiveButton("Select", null);
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Set the maximum date to exactly 18 years ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        long maxDateMillis = calendar.getTimeInMillis();
        datePicker.setMaxDate(maxDateMillis);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.ted_image_picker_primary_pressed));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int birthMonth = datePicker.getMonth() + 1;
                        String dateToDisplay = birthMonth + "-" + datePicker.getDayOfMonth() + "-" + datePicker.getYear();

                        dob = dateToDisplay;

                        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

                        if (currentMonth > birthMonth) age = currentYear - datePicker.getYear();
                        else age = currentYear - datePicker.getYear() - 1;

                        binding.textviewDateOfBirth.setText(dateToDisplay);
                        dialog.dismiss();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }
}