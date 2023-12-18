package com.oho.oho.views.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.adapters.UpcomingDatesAdapter;
import com.oho.oho.databinding.ActivityUpcomingDatesBinding;
import com.oho.oho.models.UpcomingDate;

import java.util.ArrayList;

public class UpcomingDatesActivity extends AppCompatActivity {
    ActivityUpcomingDatesBinding binding;
    ArrayList<UpcomingDate> upcomingDateArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityUpcomingDatesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDummyDates();
        UpcomingDatesAdapter adapter = new UpcomingDatesAdapter(upcomingDateArrayList,this);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);
    }

    private ArrayList<UpcomingDate> setDummyDates(){
        String [] names = {"Leah Tharin","Erin Massey Dawson","Miss Joanna Sporer"};
        String [] photos = {"https://oho-assets.s3.amazonaws.com/dca7bff245c74416bc6edf69ce5ebc94.jpeg","https://oho-assets.s3.amazonaws.com/248d96b1c5b7427bb465dd76816a2ad4.jpeg","https://oho-assets.s3.amazonaws.com/74edac2079dc41c4810dfc3b0c848356.jpg"};
        String [] dayTimes = {"FRI, 8:00 PM","SAT, 7:00 PM","SUN, 11:00 AM"};
        String [] locations = {"NORTH END coffee roasters Dhanmondi","NORTH END coffee roasters Gulshan","NORTH END coffee roasters Shahabuddin Park"};
        int [] chatIds = {1,2,3};
        String [] channelNames = {"6289e5e8-b2c1-4c08-a4e6-35a5b28ce685","6289e5e8-b2c1-4c08-a4e6-35a5b28ce685","6289e5e8-b2c1-4c08-a4e6-35a5b28ce685"};
        int [] ids = {21,22,23};
        for (int i=0; i<names.length;i++){
            UpcomingDate upcomingDate = new UpcomingDate();
            upcomingDate.setId(ids[i]);
            upcomingDate.setFullName(names[i]);
            upcomingDate.setAvailability(dayTimes[i]);
            upcomingDate.setLocation(locations[i]);
            upcomingDate.setProfilePhoto(photos[i]);
            upcomingDate.setChatId(chatIds[i]);
            upcomingDate.setChannelName(channelNames[i]);

            upcomingDateArrayList.add(upcomingDate);
        }
        return upcomingDateArrayList;
    }
}