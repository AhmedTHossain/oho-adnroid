package com.oho.oho.views.registration;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oho.oho.R;

public class IntroFragment extends Fragment {
    private String onBoardingUserName;
    public IntroFragment(String onBoardingUserName) {
        this.onBoardingUserName = onBoardingUserName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        TextView welcomeText = view.findViewById(R.id.text_welcome);

//        String onBoardingUserFirstName = onBoardingUserName.split(" ")[0];
//        String welcomeMessage = "Hi "+ onBoardingUserFirstName +", welcome to...";
//        welcomeText.setText(welcomeMessage);

        // Inflate the layout for this fragment
        return view;
    }
}