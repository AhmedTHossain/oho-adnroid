package com.oho.oho.views.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentHomeBinding;
import com.oho.oho.views.settings.AvailabilitySettingsActivity;

import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);

        //finding which day of week is today in order to check if its the dating phase or matching phase. So that the appropriate UI can be shown based on that.
        Date date = new Date();
        CharSequence time = DateFormat.format("E", date.getTime()); // gives like (Wednesday)

        if (String.valueOf(time).equals("Mon"))
            startActivity(new Intent(requireActivity(), CheckAvailabilityActivity.class));

        binding.dayText.setText(String.valueOf(time));

        Calendar c = Calendar.getInstance();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}