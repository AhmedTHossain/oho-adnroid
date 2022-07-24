package com.oho.oho.views.prompt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oho.oho.R;
import com.oho.oho.adapters.PromptAdapter;
import com.oho.oho.databinding.ActivityPromptBinding;
import com.oho.oho.interfaces.OnPromptQuestionSelectedListener;

import java.util.ArrayList;

public class PromptActivity extends AppCompatActivity implements OnPromptQuestionSelectedListener {

    ActivityPromptBinding binding;
    private EditText answerText;
    private RecyclerView recyclerView;
    private TextView titleText, screenTitleText;
    private LinearLayout answerLayout;
    private Animation animShow, animHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_OHO);
        binding = ActivityPromptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recyclerviewPromptQuestions;
        answerText = binding.edittextAnswerPrompt;
        titleText = binding.textTitleSelectPrompt;
        screenTitleText = binding.textScreenTitle;
        answerLayout = binding.layoutQuestionSelected;

        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);

        //TODO: later will be replaced by the API response of prompts
        ArrayList<String> promptList = new ArrayList<>();
        promptList.add("what is your favorite color?");
        promptList.add("what is your spirit animal?");
        promptList.add("I am passionate about...");
        promptList.add("recently, I learned...");
        promptList.add("ideal Sunday for me is...");
        promptList.add("in a partner, I'm looking for...");
        promptList.add("most adventurous thing I did was..");
        promptList.add("whiskey or wine?");
        promptList.add("ideal sunday for me is...");

        PromptAdapter adapter = new PromptAdapter(this,promptList,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPromptQuestionSelected(String s) {
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
            screenTitleText.setText("Select Prompt");
            titleText.setText("Prompts");

            answerText.setVisibility(View.GONE);
            answerText.startAnimation(animHide);
            answerLayout.setVisibility(View.GONE);
            answerLayout.startAnimation(animHide);

            recyclerView.startAnimation(animShow);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}