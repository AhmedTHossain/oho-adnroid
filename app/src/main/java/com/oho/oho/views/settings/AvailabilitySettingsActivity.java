package com.oho.oho.views.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.adapters.SavedSlotsAdapter;
import com.oho.oho.databinding.ActivityAvailabilitySettingsBinding;
import com.oho.oho.viewmodels.AvailabilitySettingsViewModel;

import java.util.ArrayList;

public class AvailabilitySettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAvailabilitySettingsBinding binding;
    ArrayList<String> selectedSlotsArray, preSelectedSlotsArray;
    private AvailabilitySettingsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityAvailabilitySettingsBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        initViewModel();

        selectedSlotsArray = new ArrayList<>();
        preSelectedSlotsArray = new ArrayList<>();

//        if (getAvailabilityConsent() != -1) {
////            setAlreadySelectedTimeSlots();
//        }
//        else
//            showCannotChangeAvailabilityDialog();

        getAvailabilityConsent();

        //Click Listeners
        binding.buttonClearSlots.setOnClickListener(this);
        binding.buttonSaveSlots.setOnClickListener(this);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AvailabilitySettingsViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_clear_slots:
                binding.friSlot1.setChecked(false);
                binding.friSlot2.setChecked(false);
                binding.friSlot3.setChecked(false);
                binding.satSlot1.setChecked(false);
                binding.satSlot2.setChecked(false);
                binding.satSlot3.setChecked(false);
                binding.sunSlot1.setChecked(false);
                binding.sunSlot2.setChecked(false);
                selectedSlotsArray.clear();
                break;
            case R.id.button_save_slots:
//                saveAvailability();
                break;
        }
    }

//    private void saveAvailability() {
//        if (selectedSlotsArray.size() != 0) {
//            //Call update availability API
//            availabilitySettingsViewModel.addAvailableTimeSlots(3, selectedSlotsArray);
//            availabilitySettingsViewModel.selectedTimeSlotsList.observe(this, slotsSelected -> {
//                storeAvailabilityConsent(1);
//
//                selectedSlotsArray.clear();
//                selectedSlotsArray.addAll(slotsSelected);
//
//                LayoutInflater li = LayoutInflater.from(getApplicationContext());
//                View slotsView = li.inflate(R.layout.availability_saved_dialog, null);
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        this);
//                // set alert_dialog.xml to alertdialog builder
//                alertDialogBuilder.setView(slotsView);
//
//                RecyclerView recyclerView = slotsView.findViewById(R.id.recyclerview_prompt_section);
//                SavedSlotsAdapter adapter = new SavedSlotsAdapter(selectedSlotsArray);
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(this));
//                recyclerView.setAdapter(adapter);
//
//                alertDialogBuilder.setCancelable(false)
//                        .setPositiveButton("CONFIRM", null)
//                        .setNegativeButton("CANCEL", null);
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                    @Override
//                    public void onShow(DialogInterface dialog) {
//                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
//                        button.setText("EDIT");
//                        button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.indicatioractive));
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//                            }
//                        });
//
//                        Button button2 = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
//                        button2.setText("OK");
//                        button2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textTitle));
//                        button2.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                finish();
//                            }
//                        });
//                    }
//                });
//                // show it
//                alertDialog.show();
//            });
//        } else
//            Toast.makeText(AvailabilitySettingsActivity.this, "You haven't selected any time slots yet!", Toast.LENGTH_SHORT).show();
//    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        String day = "";

        switch (view.getId()) {
            case R.id.fri_slot_1:
            case R.id.fri_slot_2:
            case R.id.fri_slot_3:
                day = "Fri";
                break;
            case R.id.sat_slot_1:
            case R.id.sat_slot_2:
            case R.id.sat_slot_3:
                day = "Sat";
                break;
            case R.id.sun_slot_1:
            case R.id.sun_slot_2:
                day = "Sun";
                break;
        }

        String slot = day + ", " + ((CheckBox) view).getText().toString();
        if (checked)
            selectedSlotsArray.add(slot);
        else
            selectedSlotsArray.remove(slot);

        Log.d("AvailabilitySettingsActivity", "selected slots = " + selectedSlotsArray.toString());
    }

//    private void setAlreadySelectedTimeSlots() {
//        availabilitySettingsViewModel.getAvailableTimeSlots(3);
//        availabilitySettingsViewModel.selectedTimeSlotsList.observe(this, slotsSelected -> {
//            if (slotsSelected.size() != 0) {
//                preSelectedSlotsArray.addAll(slotsSelected);
//
//                LayoutInflater li = LayoutInflater.from(getApplicationContext());
//                View slotsView = li.inflate(R.layout.availability_saved_dialog, null);
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        this);
//                // set alert_dialog.xml to alertdialog builder
//                alertDialogBuilder.setView(slotsView);
//
//                RecyclerView recyclerView = slotsView.findViewById(R.id.recyclerview_prompt_section);
//                SavedSlotsAdapter adapter = new SavedSlotsAdapter(preSelectedSlotsArray);
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(this));
//                recyclerView.setAdapter(adapter);
//
//                alertDialogBuilder.setCancelable(false)
//                        .setPositiveButton("CONFIRM", null)
//                        .setNegativeButton("CANCEL", null);
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                    @Override
//                    public void onShow(DialogInterface dialog) {
//                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
//                        button.setText("EDIT");
//                        button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.indicatioractive));
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//                            }
//                        });
//
//                        Button button2 = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
//                        button2.setText("OK");
//                        button2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textTitle));
//                        button2.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                finish();
//                            }
//                        });
//                    }
//                });
//                // show it
//                alertDialog.show();
//            } else
//                Toast.makeText(getApplicationContext(), "Please select your available time slots for the week", Toast.LENGTH_SHORT).show();
//        });
//    }

    private void storeAvailabilityConsent(int available) {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("available", available);
        editor.commit();
    }

    private void getAvailabilityConsent() {
        viewModel.checkIfAvailable(187);
        viewModel.isAvailable.observe(this, isAvailable ->{
            if (isAvailable){
                Toast.makeText(this,"Available for the weekend!",Toast.LENGTH_SHORT).show();
            }
            else
                showCannotChangeAvailabilityDialog();
        });
    }

    private void showCannotChangeAvailabilityDialog() {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View slotsView = li.inflate(R.layout.availabillity_cannot_change_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(slotsView);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.indicatioractive));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        onBackPressed();
                        finish();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }
}