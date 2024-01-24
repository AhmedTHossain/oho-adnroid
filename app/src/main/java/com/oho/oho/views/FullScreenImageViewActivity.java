package com.oho.oho.views;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.oho.oho.R;
import com.oho.oho.databinding.ActivityFullScreenImageViewBinding;
import com.oho.oho.utils.HelperClass;

import java.io.IOException;

public class FullScreenImageViewActivity extends AppCompatActivity {
    private ActivityFullScreenImageViewBinding binding;
    private boolean isPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullScreenImageViewBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());

        String imageUrl = getIntent().getStringExtra("image_url");
        String imageURLWithSuffix = imageUrl+"__compressed.jpeg";

        Glide.with(this)
                .load(imageURLWithSuffix).fitCenter()
                .into(binding.imageview);

//        Glide.with(this)
//                .load(imageURLWithSuffix)
//                .fitCenter()
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        // Get the orientation and apply rotation if needed
//                        int orientation = getExifOrientation(imageURLWithSuffix);
//
//                        // Rotate the Drawable based on the orientation
//                        Drawable rotatedDrawable = rotateDrawableIfNeeded(resource, orientation);
//
//                        // Set the rotated Drawable to the ImageView
//                        binding.imageview.setImageDrawable(rotatedDrawable);
//                        return true;
//                    }
//                })
//                .into(binding.imageview);

// Helper method to get Exif orientation
//        private int getExifOrientation(String imageUrl) {
//            try {
//                ExifInterface exif = new ExifInterface(imageUrl);
//                return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return ExifInterface.ORIENTATION_NORMAL;
//            }
//        }

// Helper method to rotate Drawable based on orientation
//        private Drawable rotateDrawableIfNeeded(Drawable drawable, int orientation) {
//            if (orientation != 0) {
//                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//                Bitmap rotatedBitmap = HelperClass.rotateBitmap(bitmap, orientation);
//                return new BitmapDrawable(getResources(), rotatedBitmap);
//            } else {
//                return drawable;
//            }
//        }


        binding.buttonRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateImageAndFillScreen(binding.imageview,90);
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

    public void rotateImageAndFillScreen(ImageView imageView, int angle) {
        // Get the dimensions of the screen.
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        // Get the dimensions of the original image.
        Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        int imageWidth = imageBitmap.getWidth();
        int imageHeight = imageBitmap.getHeight();

        // Calculate the current scale factor.
        float currentScaleFactor = Math.min(screenWidth / (float) imageWidth, screenHeight / (float) imageHeight);

        // Rotate the image.
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        // Apply the current scale factor to the matrix to account for previous scaling.
        matrix.postScale(currentScaleFactor, currentScaleFactor);

        // Calculate the new dimensions of the rotated image.
        RectF rectF = new RectF(0, 0, imageWidth, imageHeight);
        matrix.mapRect(rectF);

        int rotatedImageWidth = Math.round(rectF.width());
        int rotatedImageHeight = Math.round(rectF.height());

        // Calculate the new scale factor to fit the rotated image on the screen.
        float newScaleFactor = Math.min(screenWidth / (float) rotatedImageWidth, screenHeight / (float) rotatedImageHeight);

        // Update the scaling transformation in the matrix.
        matrix.postScale(newScaleFactor, newScaleFactor);

        // Create a new bitmap with the rotated and scaled image.
        Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageWidth, imageHeight, matrix, true);

        // Set the new bitmap to the image view.
        imageView.setImageBitmap(rotatedBitmap);
    }
}