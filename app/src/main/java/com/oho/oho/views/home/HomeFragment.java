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
import com.oho.oho.interfaces.ChangeHomeUiListener;
import com.oho.oho.views.settings.AvailabilitySettingsActivity;

import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ChangeHomeUiListener changeHomeUiListener;

    public HomeFragment( ChangeHomeUiListener changeHomeUiListener) {
        // Required empty public constructor
        this.changeHomeUiListener = changeHomeUiListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        changeHomeUiListener.changeHomeUi();

        Calendar c = Calendar.getInstance();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}