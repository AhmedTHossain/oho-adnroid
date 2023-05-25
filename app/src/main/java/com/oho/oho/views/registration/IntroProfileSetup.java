package com.oho.oho.views.registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentIntroProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;


public class IntroProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentIntroProfileSetupBinding binding;

    public IntroProfileSetup() {
        // Required empty public constructor
    }

    public IntroProfileSetup(OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIntroProfileSetupBinding.inflate(inflater, container, false);

        binding.buttonNextIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onScreenChange("next","intro");
            }
        });

        binding.textviewDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateInputDialog();
            }
        });

        return binding.getRoot();
    }

    private void showDateInputDialog() {
        LayoutInflater li = LayoutInflater.from(requireContext());
        View promptsView = li.inflate(R.layout.date_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final DatePicker datePicker = promptsView.findViewById(R.id.datepicker);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Select", null);
        AlertDialog alertDialog = alertDialogBuilder.create();

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