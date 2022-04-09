package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oho.oho.R;
import com.oho.oho.models.Profile;

public class CompleteProfileActivity extends AppCompatActivity {

    private TextView nameAgeText, locationText, professionText, genderText, heightText, religionText, vaccinatedText, raceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        //fetching newly registered profile
        SharedPreferences mPrefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String profile = mPrefs.getString("profile","");
        Profile userProfile = gson.fromJson(profile, Profile.class);

        Log.d("CompleteProfileActivity","user profile id in complete profile activity = "+userProfile.getId());

        nameAgeText    = findViewById(R.id.text_name);
        locationText   = findViewById(R.id.text_location);
        professionText = findViewById(R.id.text_profession);
        genderText     = findViewById(R.id.text_gender);
        heightText     = findViewById(R.id.text_height);
        religionText   = findViewById(R.id.text_religion);
        vaccinatedText = findViewById(R.id.text_vaccinated);
        raceText       = findViewById(R.id.text_race);

        nameAgeText.setText(userProfile.getName());

        String location = userProfile.getCity() + ", " + userProfile.getState();
        locationText.setText(location);

        professionText.setText(userProfile.getOccupation());
        genderText.setText(userProfile.getSex());
        heightText.setText(String.valueOf(userProfile.getHeight()));
        religionText.setText(userProfile.getReligion());
        vaccinatedText.setText(userProfile.getVaccinated());
        raceText.setText(userProfile.getRace());
    }
}