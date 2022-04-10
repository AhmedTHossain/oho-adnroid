package com.oho.oho.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.CompleteProfileViewModel;
import com.oho.oho.views.prompt.PromptQuestionActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CompleteProfileViewModel completeProfileViewModel;
    private Profile userProfile;
    private static final int PICKER_REQUEST_CODE = 1;
    private static final int SELECT_PICTURE = 200;
    private TextView nameAgeText, locationText, professionText, genderText, heightText, religionText, vaccinatedText, raceText, textButtonSave;
    private EditText editTextBio;
    private CardView buttonPickImage, buttonPickFirstImage, buttonPickSecondImage, buttonPickThirdImage, selectButtonPrompt1, selectButtonPrompt2, selectButtonPrompt3;
    private CircleImageView profileImageVIew, firstImageView, secondImageView, thirdImageView;

    private String imageFor = "";

    private ActivityResultLauncher<Intent> galleryPickResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        initViewModel();

        //fetching newly registered profile
        SharedPreferences mPrefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String profile = mPrefs.getString("profile","");
        userProfile = gson.fromJson(profile, Profile.class);

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

        selectButtonPrompt1   = findViewById(R.id.card_prompt_question_1);
        selectButtonPrompt2   = findViewById(R.id.card_prompt_question_2);
        selectButtonPrompt3   = findViewById(R.id.card_prompt_question_3);

        nameAgeText.setText(userProfile.getName());

        String location = userProfile.getCity() + ", " + userProfile.getState();
        locationText.setText(location);

        professionText.setText(userProfile.getOccupation());
        genderText.setText(userProfile.getSex());
        heightText.setText(String.valueOf(userProfile.getHeight()));
        religionText.setText(userProfile.getReligion());

        if (userProfile.getVaccinated().equals("Yes"))
            vaccinatedText.setText("Vaccinated");
        else
            vaccinatedText.setText("Not Vaccinated");
        raceText.setText(userProfile.getRace());

        buttonPickImage.setOnClickListener(this);
        buttonPickFirstImage.setOnClickListener(this);
        buttonPickSecondImage.setOnClickListener(this);
        buttonPickThirdImage.setOnClickListener(this);
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
                                        break;
                                    case "first":
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(firstImageView);

                                        buttonPickFirstImage.setVisibility(View.GONE);
                                        firstImageView.setVisibility(View.VISIBLE);
                                        break;
                                    case "second":
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(secondImageView);

                                        buttonPickSecondImage.setVisibility(View.GONE);
                                        secondImageView.setVisibility(View.VISIBLE);
                                        break;
                                    case "third":
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(thirdImageView);

                                        buttonPickThirdImage.setVisibility(View.GONE);
                                        thirdImageView.setVisibility(View.VISIBLE);
                                        break;
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_pick_profile_image )
            showBottomSheetDialog("profile");
        if (v.getId() == R.id.button_pick_first_image )
            showBottomSheetDialog("first");
        if (v.getId() == R.id.button_pick_second_image )
            showBottomSheetDialog("second");
        if (v.getId() == R.id.button_third_profile_image )
            showBottomSheetDialog("third");
        if (v.getId() == R.id.button_text_save_profile){
            if (!TextUtils.isEmpty(editTextBio.getText())) {
                userProfile.setBio(editTextBio.getText().toString());
                completeProfileViewModel.updateUser(userProfile);
            } else
                Toast.makeText(this, "Enter a bio first!", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.card_prompt_question_1){
            navigateToPromptQuestionActivity();
        }
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

    public void navigateToPromptQuestionActivity(){
        startActivity(new Intent(this, PromptQuestionActivity.class));
    }
}