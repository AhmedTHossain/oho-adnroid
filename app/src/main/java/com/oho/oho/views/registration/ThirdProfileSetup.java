package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentThirdProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;

public class ThirdProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentThirdProfileSetupBinding binding;
    public ThirdProfileSetup() {
        // Required empty public constructor
    }

    public ThirdProfileSetup(OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThirdProfileSetupBinding.inflate(inflater, container, false);

        binding.buttonNextThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onScreenChange("next","third");
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}