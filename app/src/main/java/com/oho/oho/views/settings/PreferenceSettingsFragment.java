package com.oho.oho.views.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentPreferenceSettingsBinding;

public class PreferenceSettingsFragment extends Fragment {

    private FragmentPreferenceSettingsBinding binding;

    public PreferenceSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPreferenceSettingsBinding.inflate(inflater,container,false);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}