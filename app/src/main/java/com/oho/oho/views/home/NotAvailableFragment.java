package com.oho.oho.views.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentHomeBinding;
import com.oho.oho.databinding.FragmentNotAvailableBinding;
import com.oho.oho.views.settings.AvailabilitySettingsActivity;

public class NotAvailableFragment extends Fragment implements View.OnClickListener{

    FragmentNotAvailableBinding binding;

    public NotAvailableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNotAvailableBinding.inflate(inflater, container, false);

        // click listeners
        binding.buttonStartMatching.setOnClickListener(this);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start_matching:
                startActivity(new Intent(requireActivity(), AvailabilitySettingsActivity.class));
                break;
        }
    }
}