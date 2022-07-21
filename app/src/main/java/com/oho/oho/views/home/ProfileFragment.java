package com.oho.oho.views.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentProfileBinding;
import com.oho.oho.views.UpdateProfileActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.buttonEditProfile.setOnClickListener(this);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.buttonEditProfile.getId()){
            Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
            intent.putExtra("BIO",binding.textviewAbout.getText().toString());
            intent.putExtra("PROMPT1",binding.textPrompt1.getText().toString());
            intent.putExtra("ANSWER1",binding.textAnswer1.getText().toString());

            startActivity(intent);
        }
    }
}