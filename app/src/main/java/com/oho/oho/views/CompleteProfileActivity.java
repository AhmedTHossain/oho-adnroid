package com.oho.oho.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.oho.oho.R;
import com.oho.oho.models.Profile;

public class CompleteProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Profile userProfile;
    private static final int PICKER_REQUEST_CODE = 1;
    private TextView nameAgeText, locationText, professionText, genderText, heightText, religionText, vaccinatedText, raceText, textButtonSave;
    private EditText editTextBio;
    private CardView buttonPickImage, buttonPickFirstImage, buttonPickSecondImage, buttonPickThirdImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

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
        titleText.setText(title);

        bottomSheetDialog.show();
    }
}