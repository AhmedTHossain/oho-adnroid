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

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentSecondProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.shawnlin.numberpicker.NumberPicker;

public class SecondProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentSecondProfileSetupBinding binding;
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
                listener.onScreenChange("next","second");
            }
        });

        binding.textviewHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHeightInputDialog();
            }
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
                        Double height = calculateHeight(feetPicker.getValue(),inchPicker.getValue());
                        String heightToDisplay = feetPicker.getValue() + "' " + inchPicker.getValue() + "\"";
                        binding.textviewHeight.setText(heightToDisplay);
                        dialog.dismiss();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }

    public double calculateHeight(int feet, int inch){
        int totalInch = (feet*12) + inch;

        return totalInch * 2.54;
    }

}