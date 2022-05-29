package com.oho.oho.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.oho.oho.R;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.databinding.ActivityLoginBinding;
import com.oho.oho.databinding.ActivityMainBinding;
import com.oho.oho.models.CompleteProfileInput;
import com.oho.oho.models.Profile;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.models.PromptPhoto;
import com.oho.oho.viewmodels.CompleteProfileViewModel;
import com.oho.oho.views.prompt.PromptQuestionActivity;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CompleteProfileViewModel completeProfileViewModel;
    private Profile userProfile;
    private PromptAnswer userPromptAnswer1, userPromptAnswer2, userPromptAnswer3;
    private static final int PICKER_REQUEST_CODE = 1;
    private static final int SELECT_PICTURE = 200;


    private String imageFor = "", firstPromptPhotoCaption, secondPromptPhotoCaption, thirdPromptPhotoCaption;

    ActivityCompleteProfileBinding binding;

    private ActivityResultLauncher<Intent> galleryPickResultLauncher;

    private CompleteProfileInput completedProfile = new CompleteProfileInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();

        //fetching newly registered profile
        SharedPreferences mPrefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String profile = mPrefs.getString("profile", "");
        userProfile = gson.fromJson(profile, Profile.class);

        Log.d("CompleteProfileActivity", "user profile id in complete profile activity = " + userProfile.getId());
        String nameAgeString = userProfile.getName() + ", " + userProfile.getAge();
        binding.textName.setText(nameAgeString);

        String location = userProfile.getCity() + ", " + userProfile.getState();
        binding.textLocation.setText(location);

        binding.textProfession.setText(userProfile.getOccupation());
        binding.textGender.setText(userProfile.getSex());
        binding.textHeight.setText(String.valueOf(userProfile.getHeight()) + " cm");
        binding.textReligion.setText(userProfile.getReligion());

        if (userProfile.getVaccinated().equals("Yes"))
            binding.textVaccinated.setText("Vaccinated");
        else
            binding.textVaccinated.setText("Not Vaccinated");
        binding.textRace.setText(userProfile.getRace());

        if (!userProfile.getBio().equals("")) {
            binding.editTextBio.setTextColor(getResources().getColor(R.color.black, null));
            binding.editTextBio.setText(userProfile.getBio());
        }

        binding.buttonPickProfileImage.setOnClickListener(this);
        binding.profileImageView.setOnClickListener(this);
        binding.firstImageView.setOnClickListener(this);
        binding.secondImageView.setOnClickListener(this);
        binding.thirdImageView.setOnClickListener(this);

        binding.buttonPickFirstImage.setOnClickListener(this);
        binding.buttonPickSecondImage.setOnClickListener(this);
        binding.buttonThirdProfileImage.setOnClickListener(this);

        binding.editTextBio.setOnClickListener(this);

        binding.buttonTextSaveProfile.setOnClickListener(this);

        binding.cardPromptQuestion1.setOnClickListener(this);
        binding.cardPromptQuestion2.setOnClickListener(this);
        binding.cardPromptQuestion3.setOnClickListener(this);

        galleryPickResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                            if (data != null) {
                                Uri uri = data.getData();

                                String filePath = getRealPathFromURI(uri);
                                Log.d("CompleteProfileActivity", "gallery photo file path = " + filePath);

                                File file = new File(filePath);

                                switch (imageFor) {
                                    case "profile":
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(binding.profileImageView);

                                        binding.buttonPickProfileImage.setVisibility(View.GONE);
                                        binding.profileImageView.setVisibility(View.VISIBLE);

                                        completedProfile.setProfilePhoto(file);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);
                                        completeProfileViewModel.uploadProfilePhoto(userProfile.getId(), file);
                                        break;
                                    case "first":
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(binding.firstImageView);

                                        binding.buttonPickFirstImage.setVisibility(View.GONE);
                                        binding.firstImageView.setVisibility(View.VISIBLE);
//                                        editTextFirstImageCaption.requestFocus();
//
                                        PromptPhoto firstPromptPhoto = new PromptPhoto();
                                        firstPromptPhoto.setFile(file);
                                        completedProfile.setFirstPromptPhoto(firstPromptPhoto);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);

                                        showCaptionInputDialog("first", file);
                                        break;
                                    case "second":
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(binding.secondImageView);

                                        binding.buttonPickSecondImage.setVisibility(View.GONE);
                                        binding.secondImageView.setVisibility(View.VISIBLE);
//                                        editTextSecondImageCaption.requestFocus();

                                        PromptPhoto secondPromptPhoto = new PromptPhoto();
                                        secondPromptPhoto.setFile(file);
                                        completedProfile.setSecondPromptPhoto(secondPromptPhoto);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);

                                        showCaptionInputDialog("second", file);
                                        break;
                                    case "third":
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(binding.thirdImageView);

                                        binding.buttonThirdProfileImage.setVisibility(View.GONE);
                                        binding.thirdImageView.setVisibility(View.VISIBLE);
//                                        editTextThirdImageCaption.requestFocus();

                                        PromptPhoto thirdPromptPhoto = new PromptPhoto();
                                        thirdPromptPhoto.setFile(file);
                                        completedProfile.setThirdPromptPhoto(thirdPromptPhoto);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);

                                        showCaptionInputDialog("third", file);
                                        break;
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_pick_profile_image || v.getId() == R.id.profile_image_view)
            showBottomSheetDialog("profile");
        if (v.getId() == R.id.button_pick_first_image || v.getId() == R.id.first_image_view)
            showBottomSheetDialog("first");
        if (v.getId() == R.id.button_pick_second_image || v.getId() == R.id.second_image_view)
            showBottomSheetDialog("second");
        if (v.getId() == R.id.button_third_profile_image || v.getId() == R.id.third_image_view)
            showBottomSheetDialog("third");
        if (v.getId() == R.id.edit_text_bio)
            showBioInputDialog();
        if (v.getId() == R.id.button_text_save_profile) {

        }
        if (v.getId() == R.id.card_prompt_question_1)
            navigateToPromptQuestionActivity(1);
        if (v.getId() == R.id.card_prompt_question_2)
            navigateToPromptQuestionActivity(2);
        if (v.getId() == R.id.card_prompt_question_3)
            navigateToPromptQuestionActivity(3);
    }

    private void showBottomSheetDialog(String imageName) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.imagepicker_bottom_sheet_dialog);

        TextView titleText = bottomSheetDialog.findViewById(R.id.text_image_picker_title);
        LinearLayout buttonCamera = bottomSheetDialog.findViewById(R.id.button_camera);
        LinearLayout buttonGallery = bottomSheetDialog.findViewById(R.id.button_gallery);

        String title = "";
        switch (imageName) {
            case "first":
                title = "Pick first photo";
                break;
            case "second":
                title = "Pick second photo";
                break;
            case "third":
                title = "Pick third photo";
                break;
            default:
                title = "Pick a profile photo";
                break;
        }
        imageFor = imageName;
        titleText.setText(title);

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromGallery();
                bottomSheetDialog.dismiss();
            }
        });

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".fileprovider", f));
                startActivityForResult(intent, 1);
            }
        });

        bottomSheetDialog.show();
    }

    private void initViewModel() {
        completeProfileViewModel = new ViewModelProvider(this).get(CompleteProfileViewModel.class);
        completeProfileViewModel.getCompletProfileInputData().observe(this, new Observer<CompleteProfileInput>() {
            @Override
            public void onChanged(CompleteProfileInput completeProfileInput) {
                completedProfile = completeProfileInput;
            }
        });
    }

    private void selectFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);

        galleryPickResultLauncher.launch(intent);
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public void navigateToPromptQuestionActivity(int promptOrderNumber) {
        Intent intent = new Intent(CompleteProfileActivity.this, PromptQuestionActivity.class);
        intent.putExtra("promptOrderNumber", promptOrderNumber);
        startActivity(intent);
    }

    //function to show alert box for entering captions for prompt photos
    private void showCaptionInputDialog(String captionfor, File file) {
        // get alert_dialog.xml view
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.caption_input_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = promptsView.findViewById(R.id.userInputDialog);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("DONE", null);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (captionfor) {
                            case "first":
                                if (!TextUtils.isEmpty(userInput.getText())) {
                                    binding.firstImageCaption.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                                    String caption = "\"" + userInput.getText().toString() + "\"";
                                    binding.firstImageCaption.setText(caption);
                                    alertDialog.dismiss();

                                    completeProfileViewModel.uploadPromptPhoto(userProfile.getId(), binding.firstImageCaption.getText().toString(), file);
                                    completeProfileViewModel.uploadedPhotoId.observe(CompleteProfileActivity.this, id -> {
                                        Log.d("CompleteProfileActivity", "profile id for first prompt photo = " + id);

                                        PromptPhoto firstPromptPhoto = new PromptPhoto();
                                        firstPromptPhoto.setFile(file);
                                        firstPromptPhoto.setCaption(binding.thirdImageCaption.getText().toString());
                                        firstPromptPhoto.setId(id);

                                        completedProfile.setFirstPromptPhoto(firstPromptPhoto);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);
                                        //uploading first prompt answer
                                        Log.d("CompleteProfileActivity", "user prompt = " + userPromptAnswer1.toString());
                                        userPromptAnswer1.setPictureId(id);
                                        completeProfileViewModel.uploadUserPrompt(userPromptAnswer1);
                                    });
                                } else
                                    Toast.makeText(CompleteProfileActivity.this, "Must enter a caption!", Toast.LENGTH_SHORT).show();
                                break;
                            case "second":
                                if (!TextUtils.isEmpty(userInput.getText())) {
                                    binding.secondImageCaption.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                                    String caption = "\"" + userInput.getText().toString() + "\"";
                                    binding.secondImageCaption.setText(caption);
                                    alertDialog.dismiss();

                                    completeProfileViewModel.uploadPromptPhoto(userProfile.getId(), binding.secondImageCaption.getText().toString(), file);
                                    completeProfileViewModel.uploadedPhotoId.observe(CompleteProfileActivity.this, id -> {
                                        Log.d("CompleteProfileActivity", "profile id for second prompt photo = " + id);

                                        PromptPhoto secondPromptPhoto = new PromptPhoto();
                                        secondPromptPhoto.setFile(file);
                                        secondPromptPhoto.setCaption(binding.secondImageCaption.getText().toString());
                                        secondPromptPhoto.setId(id);

                                        completedProfile.setFirstPromptPhoto(secondPromptPhoto);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);
                                        //uploading first prompt answer
                                        Log.d("CompleteProfileActivity", "user prompt = " + userPromptAnswer2.toString());
                                        userPromptAnswer2.setPictureId(id);
                                        completeProfileViewModel.uploadUserPrompt(userPromptAnswer2);
                                    });
                                } else
                                    Toast.makeText(CompleteProfileActivity.this, "Must enter a caption!", Toast.LENGTH_SHORT).show();
                                break;
                            case "third":
                                if (!TextUtils.isEmpty(userInput.getText())) {
                                    binding.thirdImageCaption.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                                    String caption = "\"" + userInput.getText().toString() + "\"";
                                    binding.thirdImageCaption.setText(caption);
                                    alertDialog.dismiss();

                                    completeProfileViewModel.uploadPromptPhoto(userProfile.getId(), binding.thirdImageCaption.getText().toString(), file);
                                    completeProfileViewModel.uploadedPhotoId.observe(CompleteProfileActivity.this, id -> {
                                        Log.d("CompleteProfileActivity", "profile id for third prompt photo = " + id);

                                        PromptPhoto thirdPromptPhoto = new PromptPhoto();
                                        thirdPromptPhoto.setFile(file);
                                        thirdPromptPhoto.setCaption(binding.thirdImageCaption.getText().toString());
                                        thirdPromptPhoto.setId(id);

                                        completedProfile.setFirstPromptPhoto(thirdPromptPhoto);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);
                                        //uploading first prompt answer
                                        Log.d("CompleteProfileActivity", "user prompt = " + userPromptAnswer3.toString());
                                        userPromptAnswer3.setPictureId(id);
                                        completeProfileViewModel.uploadUserPrompt(userPromptAnswer3);
                                    });
                                } else
                                    Toast.makeText(CompleteProfileActivity.this, "Must enter a caption!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
        });

        // show it
        alertDialog.show();
    }

    //function to show alert box for entering bio
    private void showBioInputDialog() {
        // get alert_dialog.xml view
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.bio_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = promptsView.findViewById(R.id.userInputDialog);
        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("DONE", null);
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(userInput.getText())) {
                            String bio = userInput.getText().toString();
                            userProfile.setBio(bio);
                            completedProfile.setBio(bio);
                            completeProfileViewModel.saveCompletProfileInputData(completedProfile);
                            completeProfileViewModel.updateUser(userProfile);

                            binding.editTextBio.setText(bio);
                            binding.editTextBio.setTextColor(getResources().getColor(R.color.black, null));
                            alertDialog.dismiss();
                        } else
                            Toast.makeText(CompleteProfileActivity.this, "Enter a bio first!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }
}