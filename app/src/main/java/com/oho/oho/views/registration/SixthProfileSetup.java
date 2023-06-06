package com.oho.oho.views.registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oho.oho.adapters.SelectedPromptAdapter;
import com.oho.oho.databinding.FragmentSixthProfileSetupBinding;
import com.oho.oho.interfaces.OnPhotoPickerPrompt;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.interfaces.OnPromptAnswerListener;
import com.oho.oho.models.NewPromptAnswer;
import com.oho.oho.models.Profile;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.models.SelectedPrompt;
import com.oho.oho.utils.ImageUtils;
import com.oho.oho.viewmodels.ProfileSetupViewModel;
import com.oho.oho.viewmodels.PromptViewModel;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SixthProfileSetup extends Fragment implements OnPhotoPickerPrompt, OnPromptAnswerListener {
    FragmentSixthProfileSetupBinding binding;
    SharedPreferences sharedPreferences;
    OnProfileSetupScreenChange listener;
    File imageFile;
    private ProfileSetupViewModel viewmodel;
    ArrayList<SelectedPrompt> selectedPrompts;

    private ImageView imageView;
    // Registers a photo picker activity launcher in single-select mode.
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    imageView.setImageURI(uri);
                    imageFile = ImageUtils.getImageFileFromUri(requireContext(), uri);
                    Log.d("PhotoPicker File: ", imageFile.getName());
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });

    public SixthProfileSetup(OnProfileSetupScreenChange listener) {
        // Required empty public constructor
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSixthProfileSetupBinding.inflate(inflater, container, false);

        selectedPrompts = retrieveStringArray();


        SelectedPromptAdapter adapter = new SelectedPromptAdapter(selectedPrompts, requireContext(), this, this);

        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setClipToPadding(false);
        binding.viewpager.setClipChildren(false);
        binding.viewpager.setUserInputEnabled(false);
        binding.viewpager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        viewmodel = new ViewModelProvider(requireActivity()).get(ProfileSetupViewModel.class);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private ArrayList<SelectedPrompt> retrieveStringArray() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("stringArray", null);
        Type type = new TypeToken<ArrayList<SelectedPrompt>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    @Override
    public void onPhotoPick(ImageView imageView) {

        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)  //app compiles and run properly even after this error
                .build());
        this.imageView = imageView;
    }

    @Override
    public void onPromptAnswer(String prompt, String answer, String caption, SpinKitView progressview) {
        if (imageFile != null) {
            progressview.setVisibility(View.VISIBLE);
            Profile profile = viewmodel.getNewUserProfile().getValue();

            NewPromptAnswer newPromptAnswer = new NewPromptAnswer();
            newPromptAnswer.setUser_id(profile.getId()); //TODO: Later replace 99 with logged in user's id
            newPromptAnswer.setPrompt(prompt);
            newPromptAnswer.setAnswer(answer);
            newPromptAnswer.setCaption(caption);
            newPromptAnswer.setImage(imageFile);

            viewmodel.uploadPromptAnswer(newPromptAnswer);
            viewmodel.ifUploaded.observe(requireActivity(), ifUploaded -> {
                if (ifUploaded) {
                    progressview.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Your answer was uploaded successfully!", Toast.LENGTH_SHORT).show();
                    if (binding.viewpager.getCurrentItem() != selectedPrompts.size() - 1) {
                        binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1);
                    } else
                        listener.onScreenChange("next","sixth");
                } else {
                    Toast.makeText(requireContext(), "Answer upload failed!", Toast.LENGTH_SHORT).show();
                }
            });
        } else
            Toast.makeText(requireContext(), "Enter a Photo before you proceed.", Toast.LENGTH_SHORT).show();
    }
}