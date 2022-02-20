package com.oho.oho.views.registration;

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

import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;

public class ProfessionInputFragment extends Fragment {

    private RegistrationViewModel viewModel;
    private EditText professionText;

    private Profile profileData = new Profile();

    public ProfessionInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profession_input, container, false);

        professionText = view.findViewById(R.id.text_profession);

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
                if (profileData.getPhone() != null){
                    professionText.setText(profileData.getOccupation());
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!TextUtils.isEmpty(professionText.getText())) {
            profileData.setOccupation(professionText.getText().toString());
            viewModel.saveRegistrationFormData(profileData);
        }
    }
}