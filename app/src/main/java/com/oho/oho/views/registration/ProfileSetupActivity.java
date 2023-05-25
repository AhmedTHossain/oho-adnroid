package com.oho.oho.views.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
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
                    setScreen(new FifthProfileSetup(this));
                break;
            case "fifth":
                if (moveTo.equals("next"))
                    setScreen(new SixthProfileSetup(this));
                break;

        }
    }

    private void initViewModel(){
        viewmodel = new ViewModelProvider(this).get(ProfileSetupViewModel.class);
    }
}