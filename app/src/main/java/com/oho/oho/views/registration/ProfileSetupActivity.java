package com.oho.oho.views.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.database.LocalDatabase;
import com.oho.oho.databinding.ActivityProfileSetupBinding;
import com.oho.oho.entities.UserProfile;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.ProfileSetupViewModel;

public class ProfileSetupActivity extends AppCompatActivity implements OnProfileSetupScreenChange {
    ActivityProfileSetupBinding binding;
    ProfileSetupViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileSetupBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());


        initViewModel();
        setScreen(new IntroProfileSetup(this));
    }

    public void setScreen(Fragment fragment) {
        binding.layoutTitle.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_profile_setup, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    @Override
    public void onScreenChange(String moveTo, String from) {
        switch (from) {
            case "intro":
                if (moveTo.equals("next"))
                    setScreen(new SecondProfileSetup(this));
                break;
            case "second":
                if (moveTo.equals("next"))
                    setScreen(new ThirdProfileSetup(this));
                break;
            case "third":
                if (moveTo.equals("next"))
                    setScreen(new FourthProfileSetup(this));
                break;
            case "fourth":
                if (moveTo.equals("next"))
                    setScreen(new OccupationBioSetup(this));
                break;
            case "occupation":
                if (moveTo.equals("next"))
                    setScreen(new FifthProfileSetup(this));
                break;
            case "fifth":
                if (moveTo.equals("next"))
                    setScreen(new SixthProfileSetup(this));
                break;
            case "sixth":
                if (moveTo.equals("next")) {
                    setScreen(new SeventProfileSetup(this));
                }
                break;
            case "seventh":
                if (moveTo.equals("next")) {
                    Profile profile = viewmodel.getNewUserProfile().getValue();
                    UserProfile userProfile = new UserProfile(profile.getAge(), profile.getBio(), profile.getCity(), profile.getDob(), profile.getEducation(), profile.getEmail(), profile.getHeight(), profile.getId(), profile.getLat(), profile.getLon(), profile.getName(), profile.getOccupation(), profile.getPhone(), null, null, profile.getRace(), profile.getReligion(), profile.getSex(), profile.getState());

                    LocalDatabase localDatabase = LocalDatabase.getInstance(this);

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            // Insert Data
                            localDatabase.userProfileDao().updateUserProfile(userProfile);
                        }
                    });

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("id", profile.getId());
                    startActivity(intent);
                }
                break;
        }
    }

    private void initViewModel() {
        viewmodel = new ViewModelProvider(this).get(ProfileSetupViewModel.class);
    }
}