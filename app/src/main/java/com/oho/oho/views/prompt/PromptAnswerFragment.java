package com.oho.oho.views.prompt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;

public class PromptAnswerFragment extends Fragment {

    public PromptAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prompt_answer, container, false);


        // Inflate the layout for this fragment
        return view;
    }
}