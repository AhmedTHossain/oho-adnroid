package com.oho.oho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.oho.oho.databinding.ActivityMainBinding;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.AvailabilitySettingsViewModel;
import com.oho.oho.views.home.CheckAvailabilityActivity;
import com.oho.oho.views.home.HomeFragment;
import com.oho.oho.views.home.MatchingPhaseFragment;
import com.oho.oho.views.home.MessagesFragment;
import com.oho.oho.views.home.NotAvailableFragment;
import com.oho.oho.views.home.ProfileFragment;
import com.oho.oho.views.home.SettingsFragment;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    private AvailabilitySettingsViewModel availabilitySettingsViewModel;

    ArrayList<String> preSelectedSlotsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //TODO: for now Dark Theme is forced stopped - once we customise the Dark Theme this line will be removed
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        initAvailabilityViewModel();

        preSelectedSlotsArray = new ArrayList<>();

        checkIfAvailable();

//        if (checkIfAvailable().equals("false")) {
//            replaceFragment(new NotAvailableFragment());
//        }
//        else {
//            Log.d("MainActivity","if available = "+checkIfAvailable());
//            replaceFragment(new HomeFragment());
//        }

        SharedPreferences mPrefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String profile = mPrefs.getString("profile", "");
        Profile userProfile = gson.fromJson(profile, Profile.class);

        binding.bottomNavigationview.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.home:
                    changeUI();
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.messages:
                    replaceFragment(new MessagesFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void checkIfAvailable() {
        //Todo: Use logged in user's id instead of the hard coded one
        availabilitySettingsViewModel.getAvailableTimeSlots(13);
        availabilitySettingsViewModel.selectedTimeSlotsList.observe(this, slotsSelected -> {
            preSelectedSlotsArray.clear();
            if (slotsSelected != null)
                preSelectedSlotsArray.addAll(slotsSelected);

            changeUI();

        });
    }

    private void changeUI() {
        //finding which day of week is today in order to check if its the dating phase or matching phase. So that the appropriate UI can be shown based on that.
        Date date = new Date();
        CharSequence time = DateFormat.format("E", date.getTime()); // gives like (Wednesday)

//        if (!String.valueOf(time).equals("Fri") && !String.valueOf(time).equals("Sat") && !String.valueOf(time).equals("Sun")) {
//            if (getAvailabilityConsent() == -1) {
//                startActivity(new Intent(this, CheckAvailabilityActivity.class));
//                finish();
//            } else if (getAvailabilityConsent() == 0){
//                replaceFragment(new NotAvailableFragment());
//            } else
//                replaceFragment(new HomeFragment());
//        } else {
//            storeAvailabilityConsent(-1);
//            replaceFragment(new MatchingPhaseFragment());
//        }

        replaceFragment(new HomeFragment());
    }

    private void initAvailabilityViewModel(){
        availabilitySettingsViewModel = new ViewModelProvider(this).get(AvailabilitySettingsViewModel.class);
    }

    private int getAvailabilityConsent(){
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getInt("available",-1);
    }
    private void storeAvailabilityConsent(int available){
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("available", available);
        editor.commit();
    }
}