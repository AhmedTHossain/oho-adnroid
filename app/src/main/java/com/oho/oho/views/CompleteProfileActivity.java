package com.oho.oho.views;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.CompleteProfileAdapter;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.interfaces.AddPromptListener;
import com.oho.oho.interfaces.OnProfilePromptClickListener;
import com.oho.oho.interfaces.OnProfilePromptDeleteListener;
import com.oho.oho.interfaces.SelectProfilePhotoListener;
import com.oho.oho.models.PromptAnswer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class CompleteProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCompleteProfileBinding binding;

    CompleteProfileAdapter adapter;
    ArrayList<PromptAnswer> profilePromptsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        //set click listeners
        binding.selectProfilePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.selectProfilePhoto.getId()){
            TedImagePicker.with(this)
                    .title("Select Profile Photo")
                    .cameraTileImage(R.drawable.ic_camera_48dp)
                    .zoomIndicator(false)
                    .cameraTileBackground(R.color.black)
                    .start(new OnSelectedListener() {
                        @Override
                        public void onSelected(@NonNull Uri uri) {
                            binding.profileImageView.setImageURI(uri);
                        }
                    });
        }
    }
}