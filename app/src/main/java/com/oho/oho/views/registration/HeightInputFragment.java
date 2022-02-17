package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;

public class HeightInputFragment extends Fragment {

    public HeightInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_height_input, container, false);



        // Inflate the layout for this fragment
        return view;
    }

    //function to convert height input to centimeters
    private double convertHeightToCm(int ftInput, int inInput){
        return ((ftInput * 12) + inInput) * 2.54;
    }
}