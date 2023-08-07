package com.oho.oho.views.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.ProfileDisplayAdapter;
import com.oho.oho.databinding.FragmentProfileBinding;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.BioUpdateRequest;
import com.oho.oho.models.Profile;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.responses.ChatRoom;
import com.oho.oho.utils.ImageUtils;
import com.oho.oho.viewmodels.ProfileViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment implements View.OnClickListener, SwipeListener {

    FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    private static final String TAG = "ProfileFragment";

    private Profile userProfile;
    private ArrayList<PromptAnswer> promptAnswers = new ArrayList<>();
    private ProfileDisplayAdapter adapter;

    private AlertDialog alertDialogUploading;
    private int profile_id = 0;
    private ChatRoom chatRoom = null;
    private Profile profile;

    File imageFile;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    userProfile.setProfilePicture(uri.toString());
                    adapter.notifyDataSetChanged();
                    imageFile = ImageUtils.getImageFileFromUri(requireContext(), uri);

                    showUploadDialog();
                    profileViewModel.uploadProfilePhoto(userProfile.getId(), imageFile);
                    profileViewModel.ifProfilePhotoUploaded.observe(requireActivity(), ifProfilePhotoUploaded -> {
                        if (ifProfilePhotoUploaded) {
                            Toast.makeText(requireContext(), "Your photo was uploaded successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Photo upload failed!", Toast.LENGTH_SHORT).show();
                        }
                        alertDialogUploading.dismiss();
                    });
                }
            });

    public ProfileFragment() {
        // Required empty public constructor
    }

    public ProfileFragment(int profile_id, ChatRoom chatRoom) {
        this.profile_id = profile_id;
        this.chatRoom = chatRoom;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        initViewModels();
        if (profile_id == 0)
            getProfile(187);
        else {
            binding.screentitle.setText("View Profile");
            getProfile(profile_id);
        }

        profileViewModel.getPromptToDelete().observe(getViewLifecycleOwner(), promptToDelete -> {
            if (promptToDelete != null) {

                profileViewModel.deletePromptAnswer(promptToDelete);
                profileViewModel.ifPromptDeleted.observe(getViewLifecycleOwner(), ifPromptDeleted -> {
                    if (ifPromptDeleted) {

                        for (PromptAnswer promptAnswer : promptAnswers) {
                            if (Objects.equals(promptAnswer.getId(), promptToDelete)) {
                                Log.d("ProfileFragment", "number of prompts before deletion = " + userProfile.getPromptAnswers().size());
                                promptAnswers.remove(promptAnswer);

                                adapter.notifyDataSetChanged();

                                break;
                            }
                        }

                    }
                });

            }
        });

        profileViewModel.getIfEditBio().observe(getViewLifecycleOwner(), ifEditBio -> {
            if (ifEditBio)
                showBioInputDialog(userProfile.getBio());
        });

        profileViewModel.getIfEditPhoto().observe(getViewLifecycleOwner(), ifEditPhoto -> {
            if (ifEditPhoto)
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)  //app compiles and run properly even after this error
                        .build());
        });

        profileViewModel.getIfAddPrompt().observe(getViewLifecycleOwner(), ifAddPrompt -> {
            if (ifAddPrompt) {
                Intent intent = new Intent(new Intent(requireContext(), AddPromptActivity.class));
                intent.putExtra("userID", userProfile.getId());
                startActivity(intent);
            } else
                Toast.makeText(requireContext(), "Oops, something went wrong, try again!", Toast.LENGTH_SHORT).show();
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
                this.promptAnswers = (ArrayList<PromptAnswer>) userProfile.getPromptAnswers();
                setProfileView();
            }
        });
    }

    private void setProfileView() {
        if (profile_id != 0)
            adapter = new ProfileDisplayAdapter(userProfile, promptAnswers, this, requireContext(), profileViewModel, profile_id, chatRoom);
        else
            adapter = new ProfileDisplayAdapter(userProfile, promptAnswers, this, requireContext(), profileViewModel);
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

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Save", null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bioEditText.setText(previousBio);
                        dialog.dismiss();
                    }
                });
        ;
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Button buttonNegative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);

//                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.textTertiary));
                button.setVisibility(View.GONE);
                buttonNegative.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));

                button.setEnabled(false);
                bioEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.equals(previousBio)) {
                            if (s.length() > 0) {
                                button.setEnabled(true);
                                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.ted_image_picker_primary_pressed));
                                button.setVisibility(View.VISIBLE);

                                // update the new bio
                                userProfile.setBio(s.toString());
                            } else {
                                button.setEnabled(false);
//                                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.textTertiary));
                                button.setVisibility(View.GONE);
                            }
                        } else {
                            button.setEnabled(false);
//                            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.textTertiary));
                            button.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().equals(previousBio)) {
                            button.setEnabled(false);
//                            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.textTertiary));
                            button.setVisibility(View.GONE);
                        }
                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BioUpdateRequest request = new BioUpdateRequest(userProfile.getId(), bioEditText.getText().toString());
                        profileViewModel.updateBio(request);
                        profileViewModel.ifBioUpdated.observe(getViewLifecycleOwner(), ifBioUpdated -> {
                            if (ifBioUpdated) {
                                userProfile.setBio(bioEditText.getText().toString());
                                adapter.notifyDataSetChanged();
                            }
                        });

                        alertDialog.dismiss();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }

    private void showUploadDialog() {
        LayoutInflater li = LayoutInflater.from(requireContext());
        View promptsView = li.inflate(R.layout.upload_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // setting up the previous bio in edit text
        TextView titleText = promptsView.findViewById(R.id.text_title_upload_dialog);
        titleText.setText("Uploading New Photo...");

        alertDialogBuilder.setCancelable(false);
        alertDialogUploading = alertDialogBuilder.create();
        // show it
        alertDialogUploading.show();
    }
}