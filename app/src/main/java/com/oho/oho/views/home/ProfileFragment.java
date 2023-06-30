package com.oho.oho.views.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.oho.oho.R;
import com.oho.oho.adapters.ProfileDisplayAdapter;
import com.oho.oho.databinding.FragmentProfileBinding;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.ProfileViewModel;
import com.oho.oho.views.UpdateProfileActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener, SwipeListener {

    FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    private static final String TAG = "ProfileFragment";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        initViewModels();
        getProfile(187);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void initViewModels() {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    private void getProfile(int userid) {
        profileViewModel.getProfile(userid);
        profileViewModel.userProfile.observe(getViewLifecycleOwner(), userProfile -> {
            if (userProfile != null) {
                setProfileView(userProfile);
            }
        });
    }

    private void setProfileView(Profile profile){
        ProfileDisplayAdapter adapter = new ProfileDisplayAdapter(profile, this, requireContext(),"self");
        binding.recyclerviewProfile.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewProfile.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSwipe(int swipeDirection, SeekBar seekBar, int id) {

    }
}