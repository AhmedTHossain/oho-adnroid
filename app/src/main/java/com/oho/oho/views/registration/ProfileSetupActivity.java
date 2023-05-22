package com.oho.oho.views.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;

public class ProfileSetupActivity extends AppCompatActivity implements OnProfileSetupScreenChange {
    ActivityProfileSetupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileSetupBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());

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

        }
    }
}