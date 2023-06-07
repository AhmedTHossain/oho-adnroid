package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.databinding.FragmentSeventProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.models.Profile;
import com.oho.oho.utils.ImageUtils;
import com.oho.oho.viewmodels.ProfileSetupViewModel;

import java.io.File;

public class SeventProfileSetup extends Fragment {
    FragmentSeventProfileSetupBinding binding;
    OnProfileSetupScreenChange listener;
    private ProfileSetupViewModel viewmodel;
    File imageFile;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    binding.imageProfilePhoto.setImageURI(uri);
                    imageFile = ImageUtils.getImageFileFromUri(requireContext(), uri);
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });

    public SeventProfileSetup(  OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSeventProfileSetupBinding.inflate(inflater,container, false);

        binding.imageProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)  //app compiles and run properly even after this error
                        .build());
            }
        });

        binding.buttonNextSeventh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initViewModel();
                Profile profile = viewmodel.getNewUserProfile().getValue();

                uploadProfilePhoto(profile.getId()); //TODO: later replace 99 with logged in user's id
                updateGenderPreference(profile.getId(),binding.buttonGroupGender.getSelectedButtons().get(0).getText());
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void uploadProfilePhoto(int user_id){
        binding.uploadPhotoProgress.setVisibility(View.VISIBLE);
        viewmodel.uploadProfilePhoto(user_id,imageFile);
        viewmodel.ifProfilePhotoUploaded.observe(requireActivity(),ifProfilePhotoUploaded -> {
            if (ifProfilePhotoUploaded){
                Toast.makeText(requireContext(), "Your photo was uploaded successfully!", Toast.LENGTH_SHORT).show();
                listener.onScreenChange("next","seventh");
            } else {
                Toast.makeText(requireContext(), "Photo upload failed!", Toast.LENGTH_SHORT).show();
                binding.imageProfilePhoto.setImageURI(null);
            }

            binding.uploadPhotoProgress.setVisibility(View.GONE);
        });
    }

    private void updateGenderPreference(int user_id,String interested_in){
        viewmodel.updateGenderPreference(user_id,interested_in);
        viewmodel.ifGenderPreferenceUpdated.observe(requireActivity(), ifGenderPreferenceUpdated ->{
            if (ifGenderPreferenceUpdated)
                Toast.makeText(requireContext(), "Your preference has been updated successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(requireContext(), "Preference update failed!", Toast.LENGTH_SHORT).show();
        });
    }

    private void initViewModel(){
        viewmodel = new ViewModelProvider(requireActivity()).get(ProfileSetupViewModel.class);
    }
}