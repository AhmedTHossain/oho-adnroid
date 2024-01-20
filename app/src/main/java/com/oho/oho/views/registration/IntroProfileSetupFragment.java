package com.oho.oho.views.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.oho.oho.databinding.FragmentIntroBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;

public class IntroProfileSetupFragment extends Fragment {
    private FragmentIntroBinding binding;
    private OnProfileSetupScreenChange listener;
    private String name;
    public IntroProfileSetupFragment() {
        // Required empty public constructor
    }

    public IntroProfileSetupFragment(OnProfileSetupScreenChange listener, String name) {
        this.listener = listener;
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIntroBinding.inflate(inflater, container, false);

        binding.textTitleMessage.setText(name);

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onScreenChange("next", "intro");
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}