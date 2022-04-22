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
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.oho.oho.R;
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
    private TextView nameAgeText, locationText, professionText, genderText, heightText, religionText, vaccinatedText, raceText, textButtonSave, editTextFirstImageCaption, editTextSecondImageCaption, editTextThirdImageCaption, editTextBio, promptQuestion1Text, promptQuestion2Text, promptQuestion3Text, promptAnswer1Text, promptAnswer2Text, promptAnswer3Text;
    private CardView buttonPickImage, buttonPickFirstImage, buttonPickSecondImage, buttonPickThirdImage, selectButtonPrompt1, selectButtonPrompt2, selectButtonPrompt3;
    private CircleImageView profileImageVIew, firstImageView, secondImageView, thirdImageView;
    private LinearLayout prompt1Layout, answer1Layout, prompt2Layout, answer2Layout, prompt3Layout, answer3Layout;
    private String imageFor = "";
    private int firstPhotoId, secondPhotoId, thirdPhotoId;
    private PromptAnswer promptAnswer1, promptAnswer2, promptAnswer3;

    private ActivityResultLauncher<Intent> galleryPickResultLauncher;

    private CompleteProfileInput completedProfile = new CompleteProfileInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        initViewModel();

        //fetching newly registered profile
        SharedPreferences mPrefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String profile      = mPrefs.getString("profile","");
        userProfile         = gson.fromJson(profile, Profile.class);

        String promptAnswer1 = mPrefs.getString("promptAnswer1","");
        String promptAnswer2 = mPrefs.getString("promptAnswer2", "");
        String promptAnswer3 = mPrefs.getString("promptAnswer3", "");

        userPromptAnswer1    = gson.fromJson(promptAnswer1, PromptAnswer.class);
        userPromptAnswer2    = gson.fromJson(promptAnswer2, PromptAnswer.class);
        userPromptAnswer3    = gson.fromJson(promptAnswer3, PromptAnswer.class);

        Log.d("CompleteProfileActivity","user profile id in complete profile activity = "+userProfile.getId());

        nameAgeText           = findViewById(R.id.text_name);
        locationText          = findViewById(R.id.text_location);
        professionText        = findViewById(R.id.text_profession);
        genderText            = findViewById(R.id.text_gender);
        heightText            = findViewById(R.id.text_height);
        religionText          = findViewById(R.id.text_religion);
        vaccinatedText        = findViewById(R.id.text_vaccinated);
        raceText              = findViewById(R.id.text_race);
        buttonPickImage       = findViewById(R.id.button_pick_profile_image);
        buttonPickFirstImage  = findViewById(R.id.button_pick_first_image);
        buttonPickSecondImage = findViewById(R.id.button_pick_second_image);
        buttonPickThirdImage  = findViewById(R.id.button_third_profile_image);
        editTextBio           = findViewById(R.id.edit_text_bio);
        textButtonSave        = findViewById(R.id.button_text_save_profile);
        profileImageVIew      = findViewById(R.id.profile_image_view);
        firstImageView        = findViewById(R.id.first_image_view);
        secondImageView       = findViewById(R.id.second_image_view);
        thirdImageView        = findViewById(R.id.third_image_view);

        prompt1Layout         = findViewById(R.id.select_prompt_1_layout);
        prompt2Layout         = findViewById(R.id.select_prompt_2_layout);
        prompt3Layout         = findViewById(R.id.select_prompt_3_layout);

        answer1Layout         = findViewById(R.id.answer_prompt_1_layout);
        answer2Layout         = findViewById(R.id.answer_prompt_2_layout);
        answer3Layout         = findViewById(R.id.answer_prompt_3_layout);

        promptQuestion1Text   = findViewById(R.id.prompt_question_1_text);
        promptQuestion2Text   = findViewById(R.id.prompt_question_2_text);
        promptQuestion3Text   = findViewById(R.id.prompt_question_3_text);

        promptAnswer1Text     = findViewById(R.id.prompt_answer_1_text);
        promptAnswer2Text     = findViewById(R.id.prompt_answer_2_text);
        promptAnswer3Text     = findViewById(R.id.prompt_answer_3_text);

        editTextFirstImageCaption  = findViewById(R.id.first_image_caption);
        editTextSecondImageCaption = findViewById(R.id.second_image_caption);
        editTextThirdImageCaption  = findViewById(R.id.third_image_caption);

        selectButtonPrompt1   = findViewById(R.id.card_prompt_question_1);
        selectButtonPrompt2   = findViewById(R.id.card_prompt_question_2);
        selectButtonPrompt3   = findViewById(R.id.card_prompt_question_3);

        String nameAgeString = userProfile.getName() + ", " + userProfile.getAge();
        nameAgeText.setText(nameAgeString);

        String location = userProfile.getCity() + ", " + userProfile.getState();
        locationText.setText(location);

        professionText.setText(userProfile.getOccupation());
        genderText.setText(userProfile.getSex());
        heightText.setText(String.valueOf(userProfile.getHeight())+" cm");
        religionText.setText(userProfile.getReligion());

        if (userProfile.getVaccinated().equals("Yes"))
            vaccinatedText.setText("Vaccinated");
        else
            vaccinatedText.setText("Not Vaccinated");
        raceText.setText(userProfile.getRace());

        buttonPickImage.setOnClickListener(this);
        profileImageVIew.setOnClickListener(this);
        firstImageView.setOnClickListener(this);
        secondImageView.setOnClickListener(this);
        thirdImageView.setOnClickListener(this);

        buttonPickFirstImage.setOnClickListener(this);
        buttonPickSecondImage.setOnClickListener(this);
        buttonPickThirdImage.setOnClickListener(this);

        editTextBio.setOnClickListener(this);

        textButtonSave.setOnClickListener(this);

        selectButtonPrompt1.setOnClickListener(this);
        selectButtonPrompt2.setOnClickListener(this);
        selectButtonPrompt3.setOnClickListener(this);

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
                                Log.d("CompleteProfileActivity","gallery photo file path = "+filePath);

                                File file = new File(filePath);

                                switch (imageFor){
                                    case "profile":
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(profileImageVIew);

                                        buttonPickImage.setVisibility(View.GONE);
                                        profileImageVIew.setVisibility(View.VISIBLE);

                                        completedProfile.setProfilePhoto(file);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);
                                        completeProfileViewModel.uploadProfilePhoto(userProfile.getId(),file);
                                        break;
                                    case "first":
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(firstImageView);

                                        buttonPickFirstImage.setVisibility(View.GONE);
                                        firstImageView.setVisibility(View.VISIBLE);
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
                                                .into(secondImageView);

                                        buttonPickSecondImage.setVisibility(View.GONE);
                                        secondImageView.setVisibility(View.VISIBLE);
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
                                                .into(thirdImageView);

                                        buttonPickThirdImage.setVisibility(View.GONE);
                                        thirdImageView.setVisibility(View.VISIBLE);
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

        if (userPromptAnswer1 != null){
            prompt1Layout.setVisibility(View.GONE);
            promptQuestion1Text.setText(userPromptAnswer1.getPromptQuestion());
            promptAnswer1Text.setText(userPromptAnswer1.getAnswer());
            answer1Layout.setVisibility(View.VISIBLE);
        } else {
            answer1Layout.setVisibility(View.GONE);
        }

        if (userPromptAnswer2 != null){
            prompt2Layout.setVisibility(View.GONE);
            promptQuestion2Text.setText(userPromptAnswer2.getPromptQuestion());
            promptAnswer2Text.setText(userPromptAnswer2.getAnswer());
            answer2Layout.setVisibility(View.VISIBLE);
        } else {
            answer2Layout.setVisibility(View.GONE);
        }

        if (userPromptAnswer3 != null){
            prompt3Layout.setVisibility(View.GONE);
            promptQuestion3Text.setText(userPromptAnswer3.getPromptQuestion());
            promptAnswer3Text.setText(userPromptAnswer3.getAnswer());
            answer3Layout.setVisibility(View.VISIBLE);
        } else {
            answer3Layout.setVisibility(View.GONE);
        }
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
        if (v.getId() == R.id.button_text_save_profile){

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
        LinearLayout buttonCamera  = bottomSheetDialog.findViewById(R.id.button_camera);
        LinearLayout buttonGallery = bottomSheetDialog.findViewById(R.id.button_gallery);

        String title = "";
        switch (imageName){
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

        bottomSheetDialog.show();
    }

    private void initViewModel(){
        completeProfileViewModel = new ViewModelProvider(this).get(CompleteProfileViewModel.class);
        completeProfileViewModel.getCompletProfileInputData().observe(this, new Observer<CompleteProfileInput>() {
            @Override
            public void onChanged(CompleteProfileInput completeProfileInput) {
                completedProfile = completeProfileInput;
            }
        });
    }

    private void selectFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);

        galleryPickResultLauncher.launch(intent);
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public void navigateToPromptQuestionActivity(int promptOrderNumber){
        Intent intent = new Intent(CompleteProfileActivity.this,PromptQuestionActivity.class);
        intent.putExtra("promptOrderNumber",promptOrderNumber);
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
                                    editTextFirstImageCaption.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                                    String caption = "\""+userInput.getText().toString()+"\"";
                                    editTextFirstImageCaption.setText(caption);
                                    alertDialog.dismiss();

                                    completeProfileViewModel.uploadPromptPhoto(userProfile.getId(),editTextFirstImageCaption.getText().toString(),file);
                                    completeProfileViewModel.uploadedPhotoId.observe(CompleteProfileActivity.this,id ->{
                                        Log.d("CompleteProfileActivity","profile id for first prompt photo = "+id);

                                        firstPhotoId = id;

                                        PromptPhoto firstPromptPhoto = new PromptPhoto();
                                        firstPromptPhoto.setFile(file);
                                        firstPromptPhoto.setCaption(editTextThirdImageCaption.getText().toString());
                                        firstPromptPhoto.setId(id);

                                        completedProfile.setFirstPromptPhoto(firstPromptPhoto);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);
                                    });
                                } else
                                    Toast.makeText(CompleteProfileActivity.this, "Must enter a caption!", Toast.LENGTH_SHORT).show();
                                break;
                            case "second":
                                if (!TextUtils.isEmpty(userInput.getText())) {
                                    editTextSecondImageCaption.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                                    String caption = "\""+userInput.getText().toString()+"\"";
                                    editTextSecondImageCaption.setText(caption);
                                    alertDialog.dismiss();

                                    completeProfileViewModel.uploadPromptPhoto(userProfile.getId(),editTextSecondImageCaption.getText().toString(),file);
                                    completeProfileViewModel.uploadedPhotoId.observe(CompleteProfileActivity.this,id ->{
                                        Log.d("CompleteProfileActivity","profile id for second prompt photo = "+id);

                                        secondPhotoId = id;

                                        PromptPhoto secondPromptPhoto = new PromptPhoto();
                                        secondPromptPhoto.setFile(file);
                                        secondPromptPhoto.setCaption(editTextSecondImageCaption.getText().toString());
                                        secondPromptPhoto.setId(id);

                                        completedProfile.setSecondPromptPhoto(secondPromptPhoto);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);
                                    });
                                } else
                                    Toast.makeText(CompleteProfileActivity.this, "Must enter a caption!", Toast.LENGTH_SHORT).show();
                                break;
                            case "third":
                                if (!TextUtils.isEmpty(userInput.getText())) {
                                    editTextThirdImageCaption.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                                    String caption = "\""+userInput.getText().toString()+"\"";
                                    editTextThirdImageCaption.setText(caption);
                                    alertDialog.dismiss();

                                    completeProfileViewModel.uploadPromptPhoto(userProfile.getId(),editTextThirdImageCaption.getText().toString(),file);
                                    completeProfileViewModel.uploadedPhotoId.observe(CompleteProfileActivity.this,id ->{
                                        Log.d("CompleteProfileActivity","profile id for third prompt photo = "+id);

                                        thirdPhotoId = id;

                                        PromptPhoto thirdPromptPhoto = new PromptPhoto();
                                        thirdPromptPhoto.setFile(file);
                                        thirdPromptPhoto.setCaption(editTextThirdImageCaption.getText().toString());
                                        thirdPromptPhoto.setId(id);

                                        completedProfile.setThirdPromptPhoto(thirdPromptPhoto);
                                        completeProfileViewModel.saveCompletProfileInputData(completedProfile);
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
    private void showBioInputDialog(){
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
                        if (!TextUtils.isEmpty(userInput.getText())){
                            String bio = userInput.getText().toString();
                            userProfile.setBio(bio);
                            completedProfile.setBio(bio);
                            completeProfileViewModel.saveCompletProfileInputData(completedProfile);
                            completeProfileViewModel.updateUser(userProfile);

                            editTextBio.setText(bio);
                            editTextBio.setTextColor(getResources().getColor(R.color.black,null));
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