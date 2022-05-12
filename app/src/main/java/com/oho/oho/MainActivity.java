package com.oho.oho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oho.oho.databinding.ActivityMainBinding;
import com.oho.oho.models.Profile;
import com.oho.oho.views.home.HomeFragment;
import com.oho.oho.views.home.MessagesFragment;
import com.oho.oho.views.home.ProfileFragment;
import com.oho.oho.views.home.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        SharedPreferences mPrefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String profile = mPrefs.getString("profile","");
        Profile userProfile = gson.fromJson(profile, Profile.class);

        binding.bottomNavigationview.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.home:
                    replaceFragment(new HomeFragment());
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

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}