package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentFourthProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;

public class FourthProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentFourthProfileSetupBinding binding;
    public FourthProfileSetup() {
        // Required empty public constructor
    }

    public FourthProfileSetup(OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFourthProfileSetupBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}