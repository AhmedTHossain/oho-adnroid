package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;

public class PhoneInputFragment extends Fragment {

    private RegistrationViewModel viewModel;
    private EditText phoneText;

    public PhoneInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_input, container, false);

        phoneText = view.findViewById(R.id.text_phone);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.getProfileData().observe(this,profile -> {
            profile.setPhone(Integer.valueOf(phoneText.getText().toString()));
            viewModel.saveProfileData(profile);
        });

    }
}