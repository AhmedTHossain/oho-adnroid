package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentOccupationBioSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.ProfileSetupViewModel;

public class OccupationBioSetup extends Fragment {
    FragmentOccupationBioSetupBinding binding;
    private OnProfileSetupScreenChange listener;
    private ProfileSetupViewModel viewmodel;

    public OccupationBioSetup(OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOccupationBioSetupBinding.inflate(inflater, container, false);

        binding.buttonNextOccupationBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onScreenChange("occupationbio", "next");
            }
        });

        String[] occupationList = getResources().getStringArray(R.array.profession_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, occupationList);

        binding.textOccupation.setDropDownAnchor(R.id.text_occupation);
        binding.textOccupation.setAdapter(adapter);

        binding.buttonNextOccupationBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.textOccupation.getText().toString().equals("")) {
                    if (!binding.textBio.getText().toString().equals("")) {
                        viewmodel = new ViewModelProvider(requireActivity()).get(ProfileSetupViewModel.class);

                        Profile profile = viewmodel.getNewUserProfile().getValue();
                        profile.setOccupation(binding.textOccupation.getText().toString());
                        profile.setBio(binding.textBio.getText().toString());
                        viewmodel.updateNewUserProfile(profile);
                        viewmodel.newUserProfile.observe(requireActivity(), newUserProfile -> {
                            Log.d("OccupationBioSetup", "occupation stored in viewmodel: " + newUserProfile.getOccupation());
                        });

                        viewmodel.registerNewUser();
                        viewmodel.uploadedNewUserProfile.observe(requireActivity(), uploadedNewUserProfile ->{
                            if (uploadedNewUserProfile!=null){
                                Log.d("OccupationBio","id of newly registered user = "+uploadedNewUserProfile.getId());
                                listener.onScreenChange("next","occupation");
                            } else
                                Toast.makeText(requireContext(),"Registration Failed! Please check your connection and try again.",Toast.LENGTH_SHORT).show();
                        });
                    } else
                        Toast.makeText(requireContext(), "Please enter a bio first!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(requireContext(), "Please enter your occupation first!", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}