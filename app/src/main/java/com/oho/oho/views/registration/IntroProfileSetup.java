package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentIntroProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;


public class IntroProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentIntroProfileSetupBinding binding;

    public IntroProfileSetup() {
        // Required empty public constructor
    }

    public IntroProfileSetup(OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIntroProfileSetupBinding.inflate(inflater, container, false);

        binding.buttonNextIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onScreenChange("next","intro");
            }
        });

        return binding.getRoot();
    }
}