package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.shawnlin.numberpicker.NumberPicker;

public class DobInputFragment extends Fragment {

    public DobInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dob_input, container, false);

        NumberPicker monthPicker = view.findViewById(R.id.number_picker_month);

        String[] data = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        monthPicker.setDisplayedValues(data);

        // Inflate the layout for this fragment
        return view;
    }
}