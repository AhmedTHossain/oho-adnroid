package com.oho.oho.views;

import android.net.Uri;
import android.os.Bundle;

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

public class CompleteProfileActivity extends AppCompatActivity
        implements
        OnProfilePromptClickListener,
        OnProfilePromptDeleteListener,
        AddPromptListener,
        SelectProfilePhotoListener {

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

        for(int i=0; i<5; i++)
            profilePromptsList.add(new PromptAnswer(i,null,null,0,null,null));

        adapter = new CompleteProfileAdapter(profilePromptsList,this,this, this,this,this);
        binding.recyclerviewProfile.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewProfile.setAdapter(adapter);
    }

    @Override
    public void onProfilePromptClick() {

    }

    @Override
    public void onProfilePromptDelete(int id) {
        profilePromptsList.remove(id);
        adapter.notifyItemRemoved(id);
    }

    @Override
    public void addPrompt() {
        profilePromptsList.add(new PromptAnswer(0,null,null,0,null,null));
        adapter.notifyItemInserted(profilePromptsList.size());
    }

    @Override
    public void onSelectProfilePhoto(int id) {
        TedImagePicker.with(this)
                .title("Select Profile Photo")
                .cameraTileImage(R.drawable.ic_camera_48dp)
                .zoomIndicator(false)
                .cameraTileBackground(R.color.black)
                .start(new OnSelectedListener() {
                    @Override
                    public void onSelected(@NonNull Uri uri) {
                        profilePromptsList.get(id).setImage(String.valueOf(uri));
                        adapter.notifyItemChanged(id);
                    }
                });
    }
}