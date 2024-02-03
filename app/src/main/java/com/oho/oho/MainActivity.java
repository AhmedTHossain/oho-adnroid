package com.oho.oho;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.oho.oho.databinding.ActivityMainBinding;
import com.oho.oho.models.notifications.ChatNotificationPayload;
import com.oho.oho.models.CreateDeviceId;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.models.NotificationPreference;
import com.oho.oho.models.Profile;
import com.oho.oho.models.notifications.LikeNotificationPayload;
import com.oho.oho.responses.chat.ChatRoom;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.viewmodels.MainViewModel;
import com.oho.oho.views.LoginActivity;
import com.oho.oho.views.chat.ChatActivity;
import com.oho.oho.views.home.HomeFragment;
import com.oho.oho.views.home.LikeYouFragment;
import com.oho.oho.views.home.MessagesFragment;
import com.oho.oho.views.home.ProfileFragment;
import com.oho.oho.views.home.SettingsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    private MainViewModel viewModel;

    ArrayList<String> preSelectedSlotsArray;
    HelperClass helperClass = new HelperClass();
    private Profile profile;
    private String token;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(MainActivity.this, "Notifications permission granted", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Snackbar snackbar = Snackbar.make(
                            binding.containermain,
                            String.format(
                                    String.format(
                                            getString(R.string.txt_error_post_notification),
                                            getString(R.string.app_name)
                                    )
                            ),
                            Snackbar.LENGTH_INDEFINITE
                    ).setAction(getString(R.string.goto_settings), v -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Intent settingsIntent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                            startActivity(settingsIntent);
                        }
                    });

                    // Set background color to white (#ffffff)
                    snackbar.getView().setBackgroundColor(Color.parseColor("#000000"));

                    // Set text color to black (#000000)
                    TextView snackbarTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    snackbarTextView.setTextColor(Color.parseColor("#FFFFFF"));

                    // Set button text color to #B42254
                    snackbar.setActionTextColor(Color.parseColor("#B42254"));

                    snackbar.show();

                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //TODO: for now Dark Theme is forced stopped - once we customise the Dark Theme this line will be removed
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        // Add this in your Application class or the main activity
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        askNotificationPermission();

//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        );

        profile = helperClass.getProfile(this);

        if (profile != null) {

            initMainViewModel();

            viewModel.replaceFragment(new HomeFragment(),getSupportFragmentManager());

            getFCMToken();

            getJwtToken(profile.getEmail());

            getNotificationPreference();

            ChatNotificationPayload chatNotificationPayload;
            LikeNotificationPayload likeNotificationPayload;

            if (getIntent().hasExtra("notificationPayload")) {
                switch (getIntent().getStringExtra("TYPE")){
                    case "like":
                        Log.d(TAG,"inside like type = "+"Yes");
                        likeNotificationPayload = (LikeNotificationPayload) getIntent().getSerializableExtra("notificationPayload");
                        viewModel.replaceFragment(new LikeYouFragment(),getSupportFragmentManager());
                        break;
                    case "chat":
                        Log.d(TAG,"inside chat type = "+"Yes");
                        chatNotificationPayload = ((ChatNotificationPayload) getIntent().getSerializableExtra("notificationPayload"));
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.putExtra("notificationPayload", chatNotificationPayload);
                        startActivity(intent);
                        break;
                }

//                notificationPayload = (ChatNotificationPayload) getIntent().getSerializableExtra("notificationPayload");
//                Toast.makeText(this, "sender name = " + notificationPayload.getChannelName(), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(this, ChatActivity.class);
//                intent.putExtra("notificationPayload", notificationPayload); //where chatroom is an instance of ChatRoom object
//                intent.putExtra("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODQxODE1OTcsImlhdCI6MTY4NDA5NTE5Nywic3ViIjo5OX0.U6TqG6oHX64ZCtYMIQjzPihu7VB2jCGcbPX6gbWUtrA"); //a newly generated token has been sent to ChatActivity
//                startActivity(intent);
//                Log.d("MainActivvity", "notification payload = " + notificationPayload);
            }
            if (getIntent().hasExtra("show"))
                if (getIntent().getStringExtra("show").equals("ProfileScreen"))
                    viewModel.replaceFragment(new ProfileFragment(),getSupportFragmentManager());
            if (getIntent().hasExtra("from")) {
                if (getIntent().getStringExtra("from").equals("ChatActivity")) {
                    viewModel.replaceFragment(new ProfileFragment(getIntent().getIntExtra("sender_id", 0), (ChatRoom) getIntent().getSerializableExtra("chatroom")),getSupportFragmentManager());
                    binding.bottomNavigationview.setSelectedItemId(R.id.profile);
                }
            }


            preSelectedSlotsArray = new ArrayList<>();

//            SharedPreferences mPrefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
//            Gson gson = new Gson();
//            String profile = mPrefs.getString("profile", "");
//            Profile userProfile = gson.fromJson(profile, Profile.class);

            binding.bottomNavigationview.setOnItemSelectedListener(item -> {

                switch (item.getItemId()) {

                    case R.id.home:
                        viewModel.replaceFragment(new HomeFragment(),getSupportFragmentManager());
                        break;
                    case R.id.like:
                        viewModel.replaceFragment(new LikeYouFragment(),getSupportFragmentManager());
                        break;
                    case R.id.profile:
                        viewModel.replaceFragment(new ProfileFragment(),getSupportFragmentManager());
                        break;
                    case R.id.messages:
                        viewModel.replaceFragment(new MessagesFragment(),getSupportFragmentManager());
                        break;
                    case R.id.settings:
                        viewModel.replaceFragment(new SettingsFragment(),getSupportFragmentManager());
                        break;
                }

                return true;
            });
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void initMainViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    public static Profile getCustomObject(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE);
        String jsonString = prefs.getString("PROFILE", null);
        if (jsonString != null) {
            return Profile.fromJsonString(jsonString);
        } else {
            // Return a default object or null if no object found in SharedPreferences
            return null;
        }
    }

    private void getJwtToken(String email) {
        JWTTokenRequest jwtTokenRequest = new JWTTokenRequest();
        jwtTokenRequest.setEmail(email);
        viewModel.getJWTToken(jwtTokenRequest);
        viewModel.jwtToken.observe(this, jwtToken -> {
            if (jwtToken != null){
                Log.d("MessageFragment","initial jwt token: "+jwtToken);
                token = jwtToken;
                helperClass.setJWTToken(this,token);
            }
            else {
                helperClass.setJWTToken(this,null);
                Toast.makeText(this, "Unable to fetch JWT Token!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getNotificationPreference(){
        NotificationPreference notificationPreference = helperClass.getNotificationPreference(this);
        if (notificationPreference == null){
            notificationPreference = new NotificationPreference(true,true,true);
            helperClass.setNotificationPreference(this,notificationPreference);
        }
    }

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
                // Log.e(TAG, "PERMISSION_GRANTED");
            } else {
                // Log.e(TAG, "NO_PERMISSION");
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void storeDeviceToken(String token, String device_type) {
        CreateDeviceId createDeviceId = new CreateDeviceId();
        createDeviceId.setToken(token);
        createDeviceId.setUserType("user");
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
                    else {
                        Toast.makeText(this, "No Device ID found in response!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getFCMToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("MainViewModel", "Fetching FCM registration token failed", task.getException());
                            return;
                        } else {
                            // Get new FCM registration token
                            String token = task.getResult();

                            // Log and toast
                            String msg = "FCM token of this device: " + token;
                            Log.d("MainViewModel", "fcm token = " + msg);

                            storeDeviceToken(token,"android");
                        }
                    }
                });
    }
}