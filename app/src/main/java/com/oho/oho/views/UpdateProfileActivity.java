package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.adapters.ProfilePromptAdapter;
import com.oho.oho.databinding.ActivityCompleteProfileBinding;
import com.oho.oho.databinding.ActivityUpdateProfileBinding;
import com.oho.oho.interfaces.OnProfilePromptClickListener;
import com.oho.oho.interfaces.OnProfilePromptDeleteListener;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.views.prompt.PromptQuestionActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class UpdateProfileActivity extends AppCompatActivity implements OnProfilePromptClickListener, OnProfilePromptDeleteListener, View.OnClickListener {
    private ActivityUpdateProfileBinding binding;
    private Animation animShow, animHide;
    private ProfilePromptAdapter adapter;

    private RelativeLayout updateProfilePhotoButton;

    String bio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_OHO);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);

        bio = getIntent().getStringExtra("BIO");

        EditText aboutEditText = binding.edittextAbout;
        TextView updateBioButton = binding.buttonUpdateAbout;

        updateProfilePhotoButton = binding.buttonUpdateProfilePhoto;
//        TextView prompt1 = binding.textPromp1;
//        TextView answer1 = binding.textAnswer1;
//
//        prompt1.setText(getIntent().getStringExtra("PROMPT1"));
//        answer1.setText(getIntent().getStringExtra("ANSWER1"));

        RecyclerView recyclerView = binding.recyclerviewPrompts;

        //TODO: now using a hard coded prompt list which will later be replaced by response of the api

        ArrayList<PromptAnswer> promptList = new ArrayList<>();

        PromptAnswer promptAnswer1 = new PromptAnswer();
        promptAnswer1.setPromptQuestion("the most adventurous thing I did was...");
        promptAnswer1.setAnswer("Sky Diving from 22,000 ft");

        PromptAnswer promptAnswer2 = new PromptAnswer();
        promptAnswer2.setPromptQuestion("in a partner, I'm looking for...");
        promptAnswer2.setAnswer("Commitment &amp; honesty");

        PromptAnswer promptAnswer3 = new PromptAnswer();
        promptAnswer3.setPromptQuestion("whiskey or wine?");
        promptAnswer3.setAnswer("Whiskey");

        PromptAnswer promptAnswer4 = new PromptAnswer();
        promptAnswer4.setPromptQuestion("Recently, I learned...");
        promptAnswer4.setAnswer("To play Cello");

        PromptAnswer promptAnswer5 = new PromptAnswer();
        promptAnswer5.setPromptQuestion("I am passionate about...");
        promptAnswer5.setAnswer("Motorcycles");

        PromptAnswer promptAnswer6 = new PromptAnswer();
        promptAnswer6.setPromptQuestion("What is your spirit animal?");
        promptAnswer6.setAnswer("Bear");

        PromptAnswer promptAnswer7 = new PromptAnswer();
        promptAnswer7.setPromptQuestion("Ideal Sunday for me is...");
        promptAnswer7.setAnswer("ZZzz..");

        promptList.add(promptAnswer1);
        promptList.add(promptAnswer2);
        promptList.add(promptAnswer3);
        promptList.add(promptAnswer4);
        promptList.add(promptAnswer5);
        promptList.add(promptAnswer6);
        promptList.add(promptAnswer7);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("UpdateProfile", "prompt list size = " + promptList.size());
        adapter = new ProfilePromptAdapter(this, promptList,this, this);

        recyclerView.setAdapter(adapter);

        aboutEditText.setText(bio);

        aboutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (!aboutEditText.getText().toString().equals(bio)) {

                        if (updateBioButton.getVisibility() != View.VISIBLE) {
                            updateBioButton.startAnimation(animShow);
                            updateBioButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        updateBioButton.setVisibility(View.GONE);
                        updateBioButton.startAnimation(animHide);
                    }
                } else {
                    updateBioButton.setVisibility(View.GONE);
                    updateBioButton.startAnimation(animHide);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Setting Click Listeners
        updateProfilePhotoButton.setOnClickListener(this);
    }

    @Override
    public void onProfilePromptClick() {
        startActivity(new Intent(this, PromptQuestionActivity.class));
    }

    @Override
    public void onProfilePromptDelete(int id) {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View slotsView = li.inflate(R.layout.prompt_delete_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(slotsView);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setText("DELETE");
                button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.indicatioractive));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                Button button2 = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                button2.setText("CANCEL");
                button2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textTitle));
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == updateProfilePhotoButton.getId()){
            TedImagePicker.with(this)
                    .title("Select Profile Photo")
                    .cameraTileImage(R.drawable.ic_camera_48dp)
                    .start(new OnSelectedListener() {
                        @Override
                        public void onSelected(@NotNull Uri uri) {
//                            showSingleImage(uri);
                            binding.photoImageView.setImageURI(uri);
                        }
                    });
        }
    }
}