package com.oho.oho.views.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.adapters.SavedSlotsAdapter;
import com.oho.oho.databinding.ActivityAvailabilitySettingsBinding;
import com.oho.oho.models.Availability;
import com.oho.oho.models.Profile;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.viewmodels.AvailabilitySettingsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class AvailabilitySettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    ActivityAvailabilitySettingsBinding binding;
    ArrayList<String> selectedSlotsArray;
    private Availability preSelectedSlots = new Availability();
    private AvailabilitySettingsViewModel viewModel;
    private Integer[] slotsArray;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityAvailabilitySettingsBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        HelperClass helperClass = new HelperClass();
        profile = helperClass.getProfile(this);

        if (profile.getId() != null) {
            initViewModel();
            selectedSlotsArray = new ArrayList<>();
            changeUI();
            setOnCheckListeners();
            binding.buttonAddAvailability.setOnClickListener(this);
        }
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AvailabilitySettingsViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_availability:
                saveAvailability();
                break;
        }
    }

    private void saveAvailability() {
        if (selectedSlotsArray.size() != 0) {
            LayoutInflater li = LayoutInflater.from(getApplicationContext());
            View slotsView = li.inflate(R.layout.availability_saved_dialog, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            // set alert_dialog.xml to alertdialog builder
            alertDialogBuilder.setView(slotsView);

            RecyclerView recyclerView = slotsView.findViewById(R.id.recyclerview_availability_selected);

            // Remove duplicates using HashSet
            selectedSlotsArray = new ArrayList<>(new HashSet<>(selectedSlotsArray));
            // Sort
            Collections.sort(selectedSlotsArray);

            SavedSlotsAdapter adapter = new SavedSlotsAdapter(selectedSlotsArray);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("SAVE", null)
                    .setNegativeButton("CANCEL", null);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    Button buttonNegative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
//                    button.setText("EDIT");
                    button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.ted_image_picker_primary_pressed));
                    buttonNegative.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Call update availability API
                            viewModel.addAvailableTimeSlots(preSelectedSlots);
                            alertDialog.dismiss();
                        }
                    });
                    buttonNegative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
            });
            // show it
            alertDialog.show();
        } else
            Toast.makeText(AvailabilitySettingsActivity.this, "You haven't selected any time slots yet!", Toast.LENGTH_SHORT).show();
    }

    private void storeAvailabilityConsent(int available) {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("available", available);
        editor.commit();
    }

    private void getAvailabilityConsent() {
        viewModel.checkIfAvailable(profile.getId());
        viewModel.isAvailable.observe(this, isAvailable -> {
            if (isAvailable) {
                Toast.makeText(this, "Available for the weekend!", Toast.LENGTH_SHORT).show();
                viewModel.getAvailableTimeSlots();
                viewModel.selectedTimeSlotsList.observe(this, availability -> {
                    if (availability != null) {
                        preSelectedSlots = availability;
                        slotsArray = availability.getAllSlotsAsArray();
                        checkAvailableSlots();
                    }
                });
            }
//            else
//                changeUI();
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

    private void changeUI() {
        //finding which day of week is today in order to check if its the dating phase or matching phase. So that the appropriate UI can be shown based on that.
        Date date = new Date();
        CharSequence time = DateFormat.format("E", date.getTime()); // gives like (Wednesday)

        if (!String.valueOf(time).equals("Fri") && !String.valueOf(time).equals("Sat") && !String.valueOf(time).equals("Sun")) {
            getAvailabilityConsent();
        } else {
            showCannotChangeAvailabilityDialog();
        }
    }

    private void checkAvailableSlots() {
        for (int i = 0; i < slotsArray.length; i++) {
            if (slotsArray[i] == 1) {
                int slotNumber = i + 1;
                switch (slotNumber) {
                    case 1:
                        selectedSlotsArray.add("FRI," + binding.slot1.getText().toString());
                        binding.slot1.setChecked(true);
                        break;
                    case 2:
                        selectedSlotsArray.add("FRI," + binding.slot2.getText().toString());
                        binding.slot2.setChecked(true);
                        break;
                    case 3:
                        selectedSlotsArray.add("FRI," + binding.slot3.getText().toString());
                        binding.slot3.setChecked(true);
                        break;
                    case 4:
                        selectedSlotsArray.add("SAT," + binding.slot4.getText().toString());
                        binding.slot4.setChecked(true);
                        break;
                    case 5:
                        selectedSlotsArray.add("SAT," + binding.slot5.getText().toString());
                        binding.slot5.setChecked(true);
                        break;
                    case 6:
                        selectedSlotsArray.add("SAT," + binding.slot6.getText().toString());
                        binding.slot6.setChecked(true);
                        break;
                    case 7:
                        selectedSlotsArray.add("SUN," + binding.slot7.getText().toString());
                        binding.slot7.setChecked(true);
                        break;
                    case 8:
                        selectedSlotsArray.add("SUN," + binding.slot8.getText().toString());
                        binding.slot8.setChecked(true);
                        break;
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.slot_1:
                if (isChecked) {
                    selectedSlotsArray.add("FRI," + binding.slot1.getText().toString());
                    preSelectedSlots.setSlot1(1);
                } else {
                    selectedSlotsArray.remove("FRI," + binding.slot1.getText().toString());
                    preSelectedSlots.setSlot1(0);
                }
                break;
            case R.id.slot_2:
                if (isChecked) {
                    selectedSlotsArray.add("FRI," + binding.slot2.getText().toString());
                    preSelectedSlots.setSlot2(1);
                } else {
                    selectedSlotsArray.remove("FRI," + binding.slot2.getText().toString());
                    preSelectedSlots.setSlot2(0);
                }
                break;
            case R.id.slot_3:
                if (isChecked) {
                    selectedSlotsArray.add("FRI," + binding.slot3.getText().toString());
                    preSelectedSlots.setSlot3(1);
                } else {
                    selectedSlotsArray.remove("FRI," + binding.slot3.getText().toString());
                    preSelectedSlots.setSlot3(0);
                }
                break;
            case R.id.slot_4:
                if (isChecked) {
                    selectedSlotsArray.add("SAT," + binding.slot4.getText().toString());
                    preSelectedSlots.setSlot4(1);
                } else {
                    selectedSlotsArray.remove("SAT," + binding.slot4.getText().toString());
                    preSelectedSlots.setSlot4(0);
                }
                break;
            case R.id.slot_5:
                if (isChecked) {
                    selectedSlotsArray.add("SAT," + binding.slot5.getText().toString());
                    preSelectedSlots.setSlot5(1);
                } else {
                    selectedSlotsArray.remove("SAT," + binding.slot5.getText().toString());
                    preSelectedSlots.setSlot5(0);
                }
                break;
            case R.id.slot_6:
                if (isChecked) {
                    selectedSlotsArray.add("SAT," + binding.slot6.getText().toString());
                    preSelectedSlots.setSlot6(1);
                } else {
                    selectedSlotsArray.remove("SAT," + binding.slot6.getText().toString());
                    preSelectedSlots.setSlot6(0);
                }
                break;
            case R.id.slot_7:
                if (isChecked) {
                    selectedSlotsArray.add("SUN," + binding.slot7.getText().toString());
                    preSelectedSlots.setSlot7(1);
                } else {
                    selectedSlotsArray.remove("SUN," + binding.slot7.getText().toString());
                    preSelectedSlots.setSlot7(0);
                }
                break;
            case R.id.slot_8:
                if (isChecked) {
                    selectedSlotsArray.add("SUN," + binding.slot8.getText().toString());
                    preSelectedSlots.setSlot8(1);
                } else {
                    selectedSlotsArray.remove("SUN," + binding.slot8.getText().toString());
                    preSelectedSlots.setSlot8(0);
                }
                break;
        }
    }

    private void setOnCheckListeners() {
        binding.slot1.setOnCheckedChangeListener(this);
        binding.slot2.setOnCheckedChangeListener(this);
        binding.slot3.setOnCheckedChangeListener(this);
        binding.slot4.setOnCheckedChangeListener(this);
        binding.slot5.setOnCheckedChangeListener(this);
        binding.slot6.setOnCheckedChangeListener(this);
        binding.slot7.setOnCheckedChangeListener(this);
        binding.slot8.setOnCheckedChangeListener(this);
    }
}