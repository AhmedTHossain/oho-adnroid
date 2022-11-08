package com.oho.oho.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.oho.oho.R;
import com.oho.oho.adapters.CompleteProfileAdapter;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.interfaces.AddPromptListener;
import com.oho.oho.interfaces.OnProfilePromptClickListener;
import com.oho.oho.interfaces.OnProfilePromptDeleteListener;
import com.oho.oho.interfaces.SelectProfilePhotoListener;
import com.oho.oho.models.Profile;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.viewmodels.CompleteProfileViewModel;
import com.oho.oho.views.prompt.PromptActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class CompleteProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCompleteProfileBinding binding;
    private CompleteProfileViewModel viewModel;
    private Uri imageUri;
    private File imageFile;
    private String layoutVisible = "";
    private Animation animShow, animHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);

        initViewModel();

        EditText aboutEditText = binding.edittextAboutCompleteProfile;
        TextView updateBioButton = binding.buttonUpdateAboutCompleteProfile;

        aboutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (!aboutEditText.getText().toString().equals("")) {

                        if (updateBioButton.getVisibility() != View.VISIBLE) {
                            updateBioButton.startAnimation(animShow);
                            updateBioButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        updateBioButton.setVisibility(View.GONE);
                        updateBioButton.startAnimation(animHide);
                    }
                } else {
                    updateBioButton.setVisibility(View.GONE);
                    updateBioButton.startAnimation(animHide);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //set click listeners
        binding.buttonUpdateProfilePhoto.setOnClickListener(this);
        binding.buttonUpdateAboutCompleteProfile.setOnClickListener(this);
        binding.layoutPrompt1.setOnClickListener(this);
        binding.layoutPrompt2.setOnClickListener(this);
        binding.layoutPrompt3.setOnClickListener(this);

//        binding.uploadProfilePhoto.setOnClickListener(this);
//        binding.updateBio.setOnClickListener(this);
//        binding.textPromptQuestion1.setOnClickListener(this);
//        binding.textPromptQuestion2.setOnClickListener(this);
//        binding.textPromptQuestion3.setOnClickListener(this);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CompleteProfileViewModel.class);
    }

    @Override
    public void onClick(View view) {

        // onClick() - upload profile photo button
        if (view.getId() == binding.buttonUpdateProfilePhoto.getId()) {
            TedImagePicker.with(this)
                    .title("Select Profile Photo")
                    .cameraTileImage(R.drawable.ic_camera_48dp)
                    .zoomIndicator(false)
                    .cameraTileBackground(R.color.black)
                    .start(new OnSelectedListener() {
                        @Override
                        public void onSelected(@NonNull Uri uri) {
                            imageUri = uri;
                            Intent intent = new Intent(CompleteProfileActivity.this, CropperActivity.class);
                            intent.putExtra("DATA", imageUri.toString());
                            startActivityForResult(intent, 101);
                        }
                    });
        }

        // onClick() - update bio button
        if (view.getId() == binding.buttonUpdateAboutCompleteProfile.getId()) {
            if (!TextUtils.isEmpty(binding.edittextAboutCompleteProfile.getText())) {
                //TODO: get the profile info from registration activity on successful account registration
                Profile profile = new Profile();
                profile.setId(18);
                profile.setBio(binding.edittextAboutCompleteProfile.getText().toString());

                viewModel.updateBio(profile);
                viewModel.ifBioUpdated.observe(this, ifBioUpdated -> {
                    if (ifBioUpdated)
                        Toast.makeText(this, "Bio uploaded successfully!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Unable to upload bio!", Toast.LENGTH_SHORT).show();
                    binding.buttonUpdateAboutCompleteProfile.setVisibility(View.GONE);

                    Profile profile1 = viewModel.getUserProfile();
                    profile1.setBio(binding.edittextAboutCompleteProfile.getText().toString());
                    viewModel.setUserProfile(profile1);
                });
            } else
                Toast.makeText(this, "Please enter a bio first!", Toast.LENGTH_SHORT).show();
        }

        // onClick() - upload prompt 1,2,3 button
        if (view.getId() == binding.layoutPrompt1.getId())
            onPromptSelect(1);
        if (view.getId() == binding.layoutPrompt2.getId())
            onPromptSelect(2);
        if (view.getId() == binding.layoutPrompt3.getId())
            onPromptSelect(3);

//        if (view.getId() == binding.uploadProfilePhoto.getId()) {
//            binding.uploadingProgress.setVisibility(View.VISIBLE);
//
//            viewModel.uploadProfilePhoto(18, imageFile, CompleteProfileActivity.this);
//            viewModel.ifUploaded.observe(this, uploadComplete -> {
//                if (uploadComplete) {
//                    binding.uploadingProgress.setVisibility(View.GONE);
//
////                    binding.profilePhotoLayout.startAnimation(animHide);
//                    binding.profilePhotoLayout.setVisibility(View.GONE);
//
//                    binding.bioLayout.startAnimation(animShow);
//                    binding.bioLayout.setVisibility(View.VISIBLE);
//
//                    layoutVisible = "bio";
//                }
//            });
//        }
//
//        if (view.getId() == binding.updateBio.getId()) {
//            binding.uploadingProgress.setVisibility(View.VISIBLE);
//            if (!TextUtils.isEmpty(binding.edittextAbout.getText())) {
//                String bioText = binding.edittextAbout.getText().toString();
//
//                Profile profile = new Profile();
//                profile.setId(18);
//                profile.setBio(bioText);
//
//                viewModel.updateBio(profile);
//                binding.bioLayout.setVisibility(View.GONE);
//
//                binding.uploadingProgress.setVisibility(View.GONE);
//                binding.promptAnswerLayout.startAnimation(animShow);
//                binding.promptAnswerLayout.setVisibility(View.VISIBLE);
//
//
//                layoutVisible = "prompt";
//            } else
//                Toast.makeText(CompleteProfileActivity.this, "Please enter a bio first!", Toast.LENGTH_SHORT).show();
//        }
//
//        if (view.getId() == binding.textPromptQuestion1.getId()){
//            Intent intent = new Intent(CompleteProfileActivity.this, PromptActivity.class);
//            intent.putExtra("PROMPT_NO",1);
//            startActivity(intent);
//        }
//
//        if (view.getId() == binding.textPromptQuestion2.getId()){
//            Intent intent = new Intent(CompleteProfileActivity.this, PromptActivity.class);
//            intent.putExtra("PROMPT_NO",2);
//            startActivity(intent);
//        }
//
//        if (view.getId() == binding.textPromptQuestion3.getId()){
//            Intent intent = new Intent(CompleteProfileActivity.this, PromptActivity.class);
//            intent.putExtra("PROMPT_NO",3);
//            startActivity(intent);
//        }
    }

    // find selected/captured image aspect ratio for toggling the image views
    public String findImageAspectRatio(Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(
                    this.getContentResolver().openInputStream(uri),
                    null,
                    options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
            if (imageWidth > imageHeight)
                return "landscape";
            else
                return "portrait";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "n/a";
    }

    // provide file object of URI and also have a copy of the file in file directory.
    public static File getFile(Context context, Uri uri) throws IOException {
        File destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }

    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 101) {
            String result = data.getStringExtra("RESULT");
            Uri resultUri = null;
            if (result != null) {
                resultUri = Uri.parse(result);
                binding.photoImageView.setImageURI(resultUri);
//                binding.uploadProfilePhoto.startAnimation(animShow);
//                binding.uploadProfilePhoto.setVisibility(View.VISIBLE);
                imageFile = new File(resultUri.getPath());
                viewModel.uploadProfilePhoto(18, imageFile, CompleteProfileActivity.this);
                viewModel.ifUploaded.observe(this, ifPhotoUploaded -> {
                    if (ifPhotoUploaded)
                        Toast.makeText(this, "Profile photo uploaded successfully!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Unable to upload profile photo!", Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences mPrefs = getSharedPreferences("PROMPT_PREF", MODE_PRIVATE);

        Gson gson = new Gson();
        String json1 = mPrefs.getString("PromptAnswer1", "");
        PromptAnswer promptAnswer1 = gson.fromJson(json1, PromptAnswer.class);

        String json2 = mPrefs.getString("PromptAnswer2", "");
        PromptAnswer promptAnswer2 = gson.fromJson(json2, PromptAnswer.class);

        String json3 = mPrefs.getString("PromptAnswer3", "");
        PromptAnswer promptAnswer3 = gson.fromJson(json3, PromptAnswer.class);

        if (promptAnswer1 != null){
            binding.textPromp1.setText(promptAnswer1.getPrompt());
            binding.textAnswer1.setText(promptAnswer1.getAnswer());
            binding.photoPrompt1.setImageURI(Uri.parse(promptAnswer1.getImage()));
        }
        if (promptAnswer2 != null){
            binding.textPromp2.setText(promptAnswer2.getPrompt());
            binding.textAnswer2.setText(promptAnswer2.getAnswer());
            binding.photoPrompt2.setImageURI(Uri.parse(promptAnswer2.getImage()));
        }
        if (promptAnswer3 != null){
            binding.textPromp3.setText(promptAnswer3.getPrompt());
            binding.textAnswer3.setText(promptAnswer3.getAnswer());
            binding.photoPrompt3.setImageURI(Uri.parse(promptAnswer3.getImage()));
        }
    }

    private void onPromptSelect(int prompt_no){
        Intent intent = new Intent(CompleteProfileActivity.this, PromptActivity.class);
        intent.putExtra("PROMPT_NO", prompt_no);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}