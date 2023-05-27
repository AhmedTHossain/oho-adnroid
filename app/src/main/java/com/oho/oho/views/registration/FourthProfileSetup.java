package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentFourthProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.ProfileSetupViewModel;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;

public class FourthProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentFourthProfileSetupBinding binding;
    private ProfileSetupViewModel viewmodel;
    private String race = "";

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

        binding.buttonNextFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!race.equals("")) {
                    if (!TextUtils.isEmpty(binding.textPhone.getText())) {
                        if (binding.textPhone.getText().toString().length() == 11) {
                            viewmodel = new ViewModelProvider(requireActivity()).get(ProfileSetupViewModel.class);

                            Profile profile = viewmodel.getNewUserProfile().getValue();
                            profile.setRace(race);
                            profile.setPhone(binding.textPhone.getText().toString());

                            viewmodel.updateNewUserProfile(profile);
                            viewmodel.newUserProfile.observe(requireActivity(), newUserProfile -> {
                                Log.d("ThirdProfileSetup", "phone stored in viewmodel: " + newUserProfile.getPhone());
                            });

                            listener.onScreenChange("next", "fourth");
                        } else
                            Toast.makeText(requireContext(), "Please enter a valid Phone number first!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(requireContext(), "Please enter your Phone number first!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(requireContext(), "Please select your Ethnicity first!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonGroupRace.setOnSelectListener((ThemedButton btn) -> {
            race = btn.getText();
            // handle selected button
            return kotlin.Unit.INSTANCE;
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}