package com.oho.oho.views.registration;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;

import java.util.Objects;

public class PhoneInputFragment extends Fragment {

    private RegistrationViewModel viewModel;
    private EditText phoneText;

    private Profile profileData = new Profile();

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
        viewModel.getRegistrationFormData().observe(getViewLifecycleOwner(), new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                profileData = profile;
            }
        });
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
//        viewModel.getRegistrationFormData().observe(getViewLifecycleOwner(), new Observer<Profile>() {
//            @Override
//            public void onChanged(Profile profile) {
//                profileData = profile;
//            }
//        });
//    }

    @Override
    public void onPause() {
        super.onPause();
        if (!TextUtils.isEmpty(phoneText.getText())) {
            profileData.setPhone(Integer.valueOf(phoneText.getText().toString()));
            viewModel.saveRegistrationFormData(profileData);
        }
    }
}