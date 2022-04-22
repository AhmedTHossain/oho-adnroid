package com.oho.oho.views.prompt;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.models.Prompt;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.viewmodels.PromptViewModel;
import com.oho.oho.views.CompleteProfileActivity;

import java.util.ArrayList;

public class PromptAnswerFragment extends Fragment {

    private PromptViewModel promptViewModel;
    private TextView promptQuestionText, savePromptAnswerButton;
    private EditText answerEditText;
    private Profile userProfile;
    private int promptPhotoId, orderNumber, prompt_id;
    private String prompt_question;

    public PromptAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prompt_answer, container, false);

        promptQuestionText = view.findViewById(R.id.text_prompt);
        savePromptAnswerButton = view.findViewById(R.id.button_save_prompt_answer);
        answerEditText = view.findViewById(R.id.edittext_answer);

        initPromptViewModel();

        SharedPreferences sharedPref = requireContext().getSharedPreferences("prompt_selected", MODE_PRIVATE);
        prompt_id = sharedPref.getInt("id", 0);
        prompt_question = sharedPref.getString("question",null);

        if (getArguments() != null) {
            promptPhotoId = getArguments().getInt("promptPhotoId");
            orderNumber = getArguments().getInt("promptNumber");
        }

        promptQuestionText.setText(prompt_question);

        savePromptAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(answerEditText.getText())) {
                    //saving prompt answer in viewmodel
                    PromptAnswer promptAnswer = new PromptAnswer();
                    promptAnswer.setPromptId(prompt_id);
                    promptAnswer.setOrderNo(orderNumber);
                    promptAnswer.setPictureId(promptPhotoId);
                    promptAnswer.setUserId(fetchNewlyRegisteredProfile().getId());
                    promptAnswer.setAnswer(answerEditText.getText().toString());

                    promptViewModel.uploadUserPrompt(promptAnswer);

                    
                    startActivity(new Intent(requireActivity(), CompleteProfileActivity.class));
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initPromptViewModel(){
        promptViewModel = new ViewModelProvider(this).get(PromptViewModel.class);
    }

    private Profile fetchNewlyRegisteredProfile(){
        //fetching newly registered profile
        SharedPreferences mPrefs = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String profile = mPrefs.getString("profile","");
        userProfile = gson.fromJson(profile, Profile.class);

        return userProfile;
    }
}