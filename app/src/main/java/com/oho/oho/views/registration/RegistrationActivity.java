package com.oho.oho.views.registration;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oho.oho.R;
import com.oho.oho.adapters.RadioListItemAdapter;
import com.oho.oho.databinding.ActivityRegistrationBinding;
import com.oho.oho.interfaces.RadioListItemClickListener;
import com.oho.oho.viewmodels.RegistrationViewModel;
import com.oho.oho.views.LoginActivity;
import com.oho.oho.views.settings.AccountSettingsActivity;
import com.shawnlin.numberpicker.NumberPicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity
        implements
        View.OnClickListener,
        RadioListItemClickListener {

    private ActivityRegistrationBinding binding;
    private RegistrationViewModel registrationViewModel;
    private RadioListItemAdapter cuisineAdapter;
    private LinearLayoutManager layoutManager;
    private int feetInput = 0;
    private int inchInput = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        initAuthViewModel();

        binding.textviewHeight.setOnClickListener(this);
        binding.textviewDateOfBirth.setOnClickListener(this);

        String[] resArray = getResources().getStringArray(R.array.cuisine_list);
        List<String> radioItemList = Arrays.asList(resArray);
        ArrayList<String> radioItemArrayList = new ArrayList<>(radioItemList);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        cuisineAdapter = new RadioListItemAdapter(radioItemArrayList,this,this);
        binding.recyclerviewCuisine.setLayoutManager(layoutManager);
        binding.recyclerviewCuisine.setAdapter(cuisineAdapter);

        //setting up click listeners
        binding.textviewDateOfBirth.setOnClickListener(this);
        binding.textviewHeight.setOnClickListener(this);
        binding.textviewLocation.setOnClickListener(this);
    }

    private void initAuthViewModel() {
        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
    }

    private void startRegistration() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.textviewDateOfBirth.getId())
            showDateInputDialog();
        if (v.getId() == binding.textviewHeight.getId())
            showHeightInputDialog();
        if (v.getId() == binding.textviewLocation.getId())
            showLocationInputDialog();
    }

    @Override
    public void radioListItemClick(int position) {

    }

    private void showHeightInputDialog(){
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.height_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final NumberPicker feetPicker = promptsView.findViewById(R.id.number_picker_feet);
        final NumberPicker inchPicker = promptsView.findViewById(R.id.number_picker_inch);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("ENTER",null);
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.indicatioractive));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

    private void showDateInputDialog(){
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.date_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final DatePicker datePicker = promptsView.findViewById(R.id.datepicker);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("SELECT",null);
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.indicatioractive));
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

    private void showLocationInputDialog(){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {

                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        try {
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );

                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();

                            String locationText = city + ", " + state;
                            binding.textviewLocation.setText(locationText);

                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            binding.textviewLocation.setText("Tap again to set your location");
            //when permission denied
            ActivityCompat.requestPermissions(RegistrationActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }
}