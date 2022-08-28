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
import androidx.appcompat.app.AppCompatDelegate;
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

public class CompleteProfileActivity extends AppCompatActivity {

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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
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