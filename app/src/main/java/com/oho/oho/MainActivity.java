package com.oho.oho;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import com.oho.oho.databinding.ActivityMainBinding;
import com.oho.oho.models.ChatNotificationPayload;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.models.Profile;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //TODO: for now Dark Theme is forced stopped - once we customise the Dark Theme this line will be removed
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        );

        profile = helperClass.getProfile(this);

        if (profile != null) {

            initMainViewModel();

            viewModel.replaceFragment(new HomeFragment(),getSupportFragmentManager());

            viewModel.getFCMToken();

            getJwtToken(profile.getEmail());

            ChatNotificationPayload notificationPayload;
            if (getIntent().hasExtra("notificationPayload")) {
                notificationPayload = (ChatNotificationPayload) getIntent().getSerializableExtra("notificationPayload");
                Toast.makeText(this, "sender name = " + notificationPayload.getChannelName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("notificationPayload", notificationPayload); //where chatroom is an instance of ChatRoom object
                intent.putExtra("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODQxODE1OTcsImlhdCI6MTY4NDA5NTE5Nywic3ViIjo5OX0.U6TqG6oHX64ZCtYMIQjzPihu7VB2jCGcbPX6gbWUtrA"); //a newly generated token has been sent to ChatActivity
                startActivity(intent);
                Log.d("MainActivvity", "notification payload = " + notificationPayload);
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
}