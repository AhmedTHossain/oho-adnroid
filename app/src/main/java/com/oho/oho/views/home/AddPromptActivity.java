package com.oho.oho.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.ybq.android.spinkit.SpinKitView;
import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.adapters.PromptAdapter;
import com.oho.oho.adapters.SelectedPromptAdapter;
import com.oho.oho.databinding.ActivityAddPromptBinding;
import com.oho.oho.interfaces.OnPhotoPickerPrompt;
import com.oho.oho.interfaces.OnPromptAnswerListener;
import com.oho.oho.interfaces.OnPromptSelectListener;
import com.oho.oho.models.NewPromptAnswer;
import com.oho.oho.models.Profile;
import com.oho.oho.models.SelectedPrompt;
import com.oho.oho.utils.ImageUtils;
import com.oho.oho.viewmodels.AddPromptViewModel;

import java.io.File;
import java.util.ArrayList;

public class AddPromptActivity extends AppCompatActivity implements OnPromptSelectListener, OnPhotoPickerPrompt, OnPromptAnswerListener {
    private AddPromptViewModel viewModel;
    private ArrayList<SelectedPrompt> promptsArrayList = new ArrayList<>();
    private PromptAdapter adapter;
    ActivityAddPromptBinding binding;
    private ArrayList<SelectedPrompt> selectedPromptsList = new ArrayList<>();
    private ImageView imageView;

    private File imageFile;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    imageView.setImageURI(uri);
                    imageFile = ImageUtils.getImageFileFromUri(this, uri);
                    Log.d("PhotoPicker File: ", imageFile.getName());
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPromptBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());
        initViewModels();
        setPromptsList();

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutAddPrompt.setVisibility(View.GONE);
                binding.viewpagerPromptAnswers.setVisibility(View.VISIBLE);

                setPromptAnswerLayout();
            }
        });
//        viewModel.getOnPromptSelect().observe(this, onPromptSelect -> {
//            if (onPromptSelect) {
//                viewModel.getSelectedPrompt().observe(this, selectedPrompt -> {
//                    if (selectedPromptsList.contains(selectedPrompt)) {
//                        selectedPromptsList.remove(selectedPrompt);
//
//                        for (SelectedPrompt prompt : promptsArrayList) {
//                            if (prompt.getPrompt().equals(selectedPrompt.getPrompt())) {
//                                prompt.setIsSelected(!selectedPrompt.getIsSelected());
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
////                       String selectedCountText = selectedPromptsList.size() + "/6";
////                       binding.textSelectionCount.setText(selectedCountText);
//                    }
//                    else {
//
//                        selectedPromptsList.add(selectedPrompt);
//
//                        for (SelectedPrompt prompt : promptsArrayList) {
//                            if (prompt.getPrompt().equals(selectedPrompt.getPrompt())) {
//                                prompt.setIsSelected(!selectedPrompt.getIsSelected());
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
////                           String selectedCountText = selectedPromptsList.size() + "/6";
////                           binding.textSelectionCount.setText(selectedCountText);
//
//                    }
//
//                });
//            }
//        });
    }

    private void setPromptAnswerLayout() {
        binding.layoutAnswerPrompt.setVisibility(View.VISIBLE);
        SelectedPromptAdapter adapter = new SelectedPromptAdapter(selectedPromptsList, this, this, this);

        binding.viewpagerPromptAnswers.setAdapter(adapter);
        binding.viewpagerPromptAnswers.setClipToPadding(false);
        binding.viewpagerPromptAnswers.setClipChildren(false);
        binding.viewpagerPromptAnswers.setUserInputEnabled(false);
        binding.viewpagerPromptAnswers.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private void initViewModels() {
        viewModel = new ViewModelProvider(this).get(AddPromptViewModel.class);
    }

    public void setPromptsList() {
        String[] promptList = getResources().getStringArray(R.array.prompt_list);
        for (String prompt : promptList) {
            SelectedPrompt selectedPrompt = new SelectedPrompt(prompt, false);
            promptsArrayList.add(selectedPrompt);
        }

        adapter = new PromptAdapter(this, promptsArrayList, this);

        binding.recyclerviewAddPrompt.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewAddPrompt.setAdapter(adapter);
    }

    @Override
    public void onPromptSelect(SelectedPrompt selectedPrompt) {
        if (selectedPromptsList.contains(selectedPrompt)) {
            selectedPromptsList.remove(selectedPrompt);

            for (SelectedPrompt prompt : promptsArrayList) {
                if (prompt.getPrompt().equals(selectedPrompt.getPrompt())) {
                    prompt.setIsSelected(!selectedPrompt.getIsSelected());
                }
            }
        } else {

            selectedPromptsList.add(selectedPrompt);

            for (SelectedPrompt prompt : promptsArrayList) {
                if (prompt.getPrompt().equals(selectedPrompt.getPrompt())) {
                    prompt.setIsSelected(!selectedPrompt.getIsSelected());
                }
            }
        }
        adapter.notifyDataSetChanged();
        if (selectedPromptsList.size() > 0) {
            binding.textSelectionCount.setVisibility(View.VISIBLE);
            binding.textCount.setText(String.valueOf(selectedPromptsList.size()));
        } else
            binding.textSelectionCount.setVisibility(View.GONE);
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

            NewPromptAnswer newPromptAnswer = new NewPromptAnswer();
            newPromptAnswer.setUser_id(getIntent().getIntExtra("userID",0));
            newPromptAnswer.setPrompt(prompt);
            newPromptAnswer.setAnswer(answer);
            newPromptAnswer.setCaption(caption);
            newPromptAnswer.setImage(imageFile);

            viewModel.uploadPromptAnswer(newPromptAnswer);
            viewModel.ifUploaded.observe(this, ifUploaded -> {
                if (ifUploaded) {
                    progressview.setVisibility(View.GONE);
                    Toast.makeText(this, "Your answer was uploaded successfully!", Toast.LENGTH_SHORT).show();
                    if (binding.viewpagerPromptAnswers.getCurrentItem() != selectedPromptsList.size() - 1) {
                        binding.viewpagerPromptAnswers.setCurrentItem(binding.viewpagerPromptAnswers.getCurrentItem() + 1);
                    } else{
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("show","ProfileScreen");
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Answer upload failed!", Toast.LENGTH_SHORT).show();
                }
            });
        } else
            Toast.makeText(this, "Enter a Photo before you proceed.", Toast.LENGTH_SHORT).show();
    }
}