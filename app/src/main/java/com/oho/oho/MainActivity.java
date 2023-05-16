package com.oho.oho;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.oho.oho.databinding.ActivityMainBinding;
import com.oho.oho.models.ChatNotificationPayload;
import com.oho.oho.models.CreateDeviceId;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.MainViewModel;
import com.oho.oho.views.chat.ChatActivity;
import com.oho.oho.views.home.HomeFragment;
import com.oho.oho.views.home.LikeYouFragment;
import com.oho.oho.views.home.MatchingPhaseFragment;
import com.oho.oho.views.home.MessagesFragment;
import com.oho.oho.views.home.ProfileFragment;
import com.oho.oho.views.home.SettingsFragment;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    private MainViewModel viewModel;

    ArrayList<String> preSelectedSlotsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //TODO: for now Dark Theme is forced stopped - once we customise the Dark Theme this line will be removed
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        );

        initAvailabilityViewModel();

        getFCMToken();

        ChatNotificationPayload notificationPayload;
        if (getIntent().hasExtra("notificationPayload")) {
            notificationPayload = (ChatNotificationPayload) getIntent().getSerializableExtra("notificationPayload");
            Toast.makeText(this, "sender name = " +notificationPayload.getChannelName(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("notificationPayload", notificationPayload); //where chatroom is an instance of ChatRoom object
            intent.putExtra("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODQxODE1OTcsImlhdCI6MTY4NDA5NTE5Nywic3ViIjo5OX0.U6TqG6oHX64ZCtYMIQjzPihu7VB2jCGcbPX6gbWUtrA"); //a newly generated token has been sent to ChatActivity
            startActivity(intent);
            Log.d("MainActivvity","notification payload = "+notificationPayload);
        }

        preSelectedSlotsArray = new ArrayList<>();

//        checkIfAvailable();

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
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.like:
                    replaceFragment(new LikeYouFragment());
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

//    private void checkIfAvailable() {
//        //Todo: Use logged in user's id instead of the hard coded one
//        availabilitySettingsViewModel.checkIfAvailable(99);
//        availabilitySettingsViewModel.isAvailable.observe(this, isAvailable -> {
//
//            //finding which day of week is today in order to check if its the dating phase or matching phase. So that the appropriate UI can be shown based on that.
//            Date date = new Date();
//            CharSequence time = DateFormat.format("E", date.getTime()); // gives like (Wednesday)
//            if (!String.valueOf(time).equals("Fri") && !String.valueOf(time).equals("Sat") && !String.valueOf(time).equals("Sun")) {
//                if (isAvailable) {
//                    startActivity(new Intent(this, CheckAvailabilityActivity.class));
//                    finish();
//                }
//            } else
//                replaceFragment(new MatchingPhaseFragment());
//        });
//    }

    private void changeUI() {
        //finding which day of week is today in order to check if its the dating phase or matching phase. So that the appropriate UI can be shown based on that.
        Date date = new Date();
        CharSequence time = DateFormat.format("E", date.getTime()); // gives like (Wednesday)

        if (!String.valueOf(time).equals("Fri") && !String.valueOf(time).equals("Sat") && !String.valueOf(time).equals("Sun")) {
//            if (getAvailabilityConsent() == -1) {
//                startActivity(new Intent(this, CheckAvailabilityActivity.class));
//                finish();
//            } else if (getAvailabilityConsent() == 0){
//                replaceFragment(new NotAvailableFragment());
//            } else
            replaceFragment(new HomeFragment());
        } else {
            storeAvailabilityConsent(-1);
            replaceFragment(new MatchingPhaseFragment());
        }

//        replaceFragment(new HomeFragment());
    }

    private void initAvailabilityViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    private int getAvailabilityConsent() {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getInt("available", -1);
    }

    private void storeAvailabilityConsent(int available) {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("available", available);
        editor.commit();
    }

    private void getFCMToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        storeDeviceToken(token, 107, "android"); //TODO: later replace the hard coded user_id with logged in user's id

                        // Log and toast
                        String msg = "FCM token of this device: " + token;
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void storeDeviceToken(String token, int user_id, String device_type) {
        CreateDeviceId createDeviceId = new CreateDeviceId();
        createDeviceId.setToken(token);
        createDeviceId.setUserId(user_id);
        createDeviceId.setDeviceType(device_type);

        viewModel.storeDeviceId(createDeviceId);
        viewModel.storedIdResponse.observe(this, storedIdResponse -> {
            if (storedIdResponse != null)
                Toast.makeText(this, "Device ID found in response!!", Toast.LENGTH_SHORT).show();
            else {
//                Toast.makeText(this, "No Device ID found in response!!", Toast.LENGTH_SHORT).show();
                viewModel.updateDeviceId(createDeviceId);
                viewModel.storedIdResponse.observe(this, updatedIdResponse -> {
                    if (updatedIdResponse != null)
                        Toast.makeText(this, "Device ID found in response!!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "No Device ID found in response!!", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

}