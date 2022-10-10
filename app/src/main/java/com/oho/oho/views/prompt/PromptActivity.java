package com.oho.oho.views.prompt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oho.oho.R;
import com.oho.oho.adapters.PromptAdapter;
import com.oho.oho.databinding.ActivityPromptBinding;
import com.oho.oho.interfaces.OnPromptQuestionSelectedListener;
import com.oho.oho.models.Prompt;
import com.oho.oho.viewmodels.PromptViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class PromptActivity extends AppCompatActivity implements OnPromptQuestionSelectedListener {

    ActivityPromptBinding binding;
    private EditText answerText;
    private RecyclerView recyclerView;
    private TextView titleText, screenTitleText, saveAnswerButton, uploadPromptButton;
    private LinearLayout answerLayout;
    private RelativeLayout buttonUpdatePhoto;
    private Animation animShow, animHide;

    private PromptViewModel promptViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_OHO);
        binding = ActivityPromptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initPromptViewModel();

        recyclerView = binding.recyclerviewPromptQuestions;
        answerText = binding.edittextAnswerPrompt;
        titleText = binding.textTitleSelectPrompt;
        screenTitleText = binding.textScreenTitle;
        answerLayout = binding.layoutQuestionSelected;
        saveAnswerButton = binding.buttonSave;
        buttonUpdatePhoto = binding.buttonUpdatePromptPhoto;
        uploadPromptButton = binding.buttonUploadPromptAnwers;

        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);

        //TODO: later will be replaced by the API response of prompts
        ArrayList<String> promptList = new ArrayList<>();
//        promptList.add("what is your favorite color?");
//        promptList.add("what is your spirit animal?");
//        promptList.add("I am passionate about...");
//        promptList.add("recently, I learned...");
//        promptList.add("ideal Sunday for me is...");
//        promptList.add("in a partner, I'm looking for...");
//        promptList.add("most adventurous thing I did was..");
//        promptList.add("whiskey or wine?");
//        promptList.add("ideal sunday for me is...");

        promptViewModel.getAllPromptList();
        promptViewModel.promptList.observe(this, prompts -> {
            Log.d("PromptActivity","number of prompts = "+prompts.size());
            if (prompts.size() != 0){
                for (Prompt prompt: prompts)
                    promptList.add(prompt.getName());

                binding.spinKit.setVisibility(View.GONE);

                binding.textScrollDisclaimer.setVisibility(View.VISIBLE);
                PromptAdapter adapter = new PromptAdapter(this,promptList,this);

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            }
        });

        answerText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (saveAnswerButton.getVisibility() != View.VISIBLE) {
                        saveAnswerButton.startAnimation(animShow);
                        saveAnswerButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    saveAnswerButton.setVisibility(View.GONE);
                    saveAnswerButton.startAnimation(animHide);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerText.setVisibility(View.GONE);
                answerText.startAnimation(animHide);
                answerText.setText(null);
                answerText.clearFocus();

                answerLayout.setVisibility(View.GONE);
                answerLayout.startAnimation(animHide);

                buttonUpdatePhoto.setVisibility(View.VISIBLE);
                buttonUpdatePhoto.setAnimation(animShow);

                titleText.setText("Photo");

                if (getIntent().getBooleanExtra("NEW",false))
                    binding.textSelectPrompImageButton.setText("Select/capture new prompt photo");
                else
                    binding.layoutPromptPicture.setVisibility(View.VISIBLE);
            }
        });

        buttonUpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TedImagePicker.with(PromptActivity.this)
                        .title("Select Profile Photo")
                        .cameraTileImage(R.drawable.ic_camera_48dp)
                        .zoomIndicator(false)
                        .cameraTileBackground(R.color.black)
                        .start(new OnSelectedListener() {
                            @Override
                            public void onSelected(@NotNull Uri uri) {
//                            showSingleImage(uri);
                                binding.photoImageView.setImageURI(uri);
                                uploadPromptButton.startAnimation(animShow);
                                uploadPromptButton.setVisibility(View.VISIBLE);
                            }
                        });
            }
        });
    }

    private void initPromptViewModel(){
        promptViewModel = new ViewModelProvider(this).get(PromptViewModel.class);
    }

    @Override
    public void onPromptQuestionSelected(String s) {
        binding.textScrollDisclaimer.setVisibility(View.GONE);
        screenTitleText.setText("Answer Prompt");
        titleText.setText("Answer");
        binding.textQuestion.setText(s);

        recyclerView.setVisibility(View.GONE);
        recyclerView.startAnimation(animHide);

        answerLayout.startAnimation(animShow);
        answerLayout.setVisibility(View.VISIBLE);

        answerText.startAnimation(animShow);
        answerText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (recyclerView.getVisibility() == View.VISIBLE)
            super.onBackPressed();
        else if (answerText.getVisibility() == View.VISIBLE){
            binding.textScrollDisclaimer.setVisibility(View.VISIBLE);
            screenTitleText.setText("Select Prompt");
            titleText.setText("Prompts");

            answerText.setVisibility(View.GONE);
            answerText.startAnimation(animHide);
            answerText.setText(null);
            answerText.clearFocus();

            answerLayout.setVisibility(View.GONE);
            answerLayout.startAnimation(animHide);

            saveAnswerButton.setVisibility(View.GONE);
            answerLayout.startAnimation(animHide);

            recyclerView.startAnimation(animShow);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else if (buttonUpdatePhoto.getVisibility() == View.VISIBLE){
            buttonUpdatePhoto.setAnimation(animHide);
            buttonUpdatePhoto.setVisibility(View.GONE);

            uploadPromptButton.setAnimation(animHide);
            uploadPromptButton.setVisibility(View.GONE);

            answerText.startAnimation(animShow);
            answerText.setVisibility(View.VISIBLE);
            answerText.clearFocus();

            answerLayout.startAnimation(animShow);
            answerLayout.setVisibility(View.VISIBLE);
        }
    }
}