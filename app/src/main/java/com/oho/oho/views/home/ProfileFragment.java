package com.oho.oho.views.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.oho.oho.R;
import com.oho.oho.adapters.ProfileDisplayAdapter;
import com.oho.oho.databinding.FragmentProfileBinding;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.Profile;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.viewmodels.ProfileViewModel;
import com.oho.oho.views.UpdateProfileActivity;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements View.OnClickListener, SwipeListener {

    FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    private static final String TAG = "ProfileFragment";

    private Profile userProfile;
    private ProfileDisplayAdapter adapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        initViewModels();
        getProfile(187);

        profileViewModel.getPromptToDelete().observe(getViewLifecycleOwner(), promptToDelete -> {
            if (promptToDelete != null) {
                ArrayList<PromptAnswer> promptAnswers = new ArrayList<>(userProfile.getPromptAnswers());
                for (PromptAnswer promptAnswer: promptAnswers) {
                    if (promptAnswer.getId().equals(promptToDelete)) {
                        promptAnswers.remove(promptAnswer);
                        userProfile.setPromptAnswers(promptAnswers);
//                        adapter.notifyItemChanged();
                        setProfileView(userProfile);
                        break;
                    }
                }
            }
        });

        profileViewModel.getIfEditBio().observe(getViewLifecycleOwner(), ifEditBio -> {
            if (ifEditBio)
                showBioInputDialog(userProfile.getBio());
        });

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
                this.userProfile = userProfile;
                setProfileView(userProfile);
            }
        });
    }

    private void setProfileView(Profile profile){
        adapter = new ProfileDisplayAdapter(profile, this, requireContext(),profileViewModel);
        binding.recyclerviewProfile.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewProfile.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSwipe(int swipeDirection, SeekBar seekBar, int id) {

    }

    private void showBioInputDialog(String previousBio) {
        LayoutInflater li = LayoutInflater.from(requireContext());
        View promptsView = li.inflate(R.layout.edit_bio_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // setting up the previous bio in edit text
        EditText bioEditText = promptsView.findViewById(R.id.edit_text_bio);
        bioEditText.setText(previousBio);
        bioEditText.requestFocus();

        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Save", null);
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.indicatioractive));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        profileViewModel.editBio();
                        dialog.dismiss();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }
}