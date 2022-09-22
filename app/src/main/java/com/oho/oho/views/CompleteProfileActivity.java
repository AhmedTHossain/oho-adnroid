package com.oho.oho.views;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.CompleteProfileAdapter;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.interfaces.AddPromptListener;
import com.oho.oho.interfaces.OnProfilePromptClickListener;
import com.oho.oho.interfaces.OnProfilePromptDeleteListener;
import com.oho.oho.interfaces.SelectProfilePhotoListener;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.viewmodels.CompleteProfileViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class CompleteProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCompleteProfileBinding binding;
    private CompleteProfileViewModel viewModel;
    private Uri imageUri;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        initViewModel();

        //set click listeners
        binding.selectProfilePhoto.setOnClickListener(this);
        binding.uploadProfilePhoto.setOnClickListener(this);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CompleteProfileViewModel.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.selectProfilePhoto.getId()) {
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

        if (view.getId() == binding.uploadProfilePhoto.getId()) {
            binding.uploadingProgress.setVisibility(View.VISIBLE);

            viewModel.uploadProfilePhoto(18, imageFile, CompleteProfileActivity.this);
            viewModel.ifUploaded.observe(this, uploadComplete -> {
                if (uploadComplete)
                    binding.uploadingProgress.setVisibility(View.GONE);
            });
        }
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
                binding.profileImageViewPortrait.setImageURI(resultUri);
                binding.uploadProfilePhoto.setVisibility(View.VISIBLE);
                imageFile = new File(resultUri.getPath());

            }
        }
    }
}