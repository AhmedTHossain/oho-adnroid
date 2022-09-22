package com.oho.oho.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.databinding.ActivityCropperBinding;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class CropperActivity extends AppCompatActivity {

    ActivityCropperBinding binding;
    String result;
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityCropperBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        readIntent();

        String dest_uri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();

        //can use a lot of options with the options variable
        UCrop.Options options = new UCrop.Options();

        options.setDimmedLayerColor(getColor(R.color.white));
        options.setCropFrameColor(getColor(R.color.black));
        options.setCropGridColor(getColor(R.color.black));
        options.setCropGridCornerColor(getColor(R.color.black));
//        options.setFreeStyleCropEnabled(true);
        options.setCropGridCornerColor(getColor(R.color.indicatioractive));
        options.setStatusBarColor(getColor(R.color.white));
        options.setActiveControlsWidgetColor(getColor(R.color.indicatioractive));

        UCrop.of(fileUri,Uri.fromFile(new File(getCacheDir(),dest_uri)))
                .withOptions(options)
                .withAspectRatio(1,1)
                .withMaxResultSize(2000,2000)
                .start(CropperActivity.this);

    }

    private void readIntent(){
        Intent intent = getIntent();
        if (intent.getExtras() != null){
            result = intent.getStringExtra("DATA");
            fileUri = Uri.parse(result);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==UCrop.REQUEST_CROP){
            final Uri resultUri = UCrop.getOutput(data);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("RESULT",resultUri+"");
            setResult(-1,returnIntent);
            finish();
        } else if (resultCode==UCrop.RESULT_ERROR){
            final Throwable cropError = UCrop.getError(data);
        }
    }
}