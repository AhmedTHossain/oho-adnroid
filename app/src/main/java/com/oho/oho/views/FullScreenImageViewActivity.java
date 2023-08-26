package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oho.oho.R;
import com.oho.oho.adapters.ProfileDisplayAdapter;
import com.oho.oho.databinding.ActivityFullScreenImageViewBinding;

public class FullScreenImageViewActivity extends AppCompatActivity {
    private ActivityFullScreenImageViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullScreenImageViewBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());

        String imageUrl = getIntent().getStringExtra("image_url");

        Glide.with(this)
                .load(imageUrl).centerInside()
                .into(binding.imageview);

        binding.buttonRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullScreenImageViewActivity.super.onBackPressed();
                finish();
            }
        });
    }

    private void rotateLayout(ImageView imageView) {
        float currentRotation = imageView.getRotation();
        float newRotation = currentRotation + 90; // Rotate by 90 degrees

        imageView.setRotation(newRotation);
    }
}