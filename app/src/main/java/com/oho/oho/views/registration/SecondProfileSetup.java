package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentSecondProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;

public class SecondProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentSecondProfileSetupBinding binding;
    public SecondProfileSetup() {
        // Required empty public constructor
    }

    public SecondProfileSetup(OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondProfileSetupBinding.inflate(inflater, container, false);

        binding.buttonNextSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onScreenChange("next","second");
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}