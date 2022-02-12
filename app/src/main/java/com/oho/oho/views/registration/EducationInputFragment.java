package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.adapters.RegistrationInputFieldAdapter;

public class EducationInputFragment extends Fragment {

    public EducationInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education_input, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        String [] data = {"Some college", "Associate’s Degree", "Bachelor’s Degree", "Graduate Degree", "Professional Degree", "No Degree"};
        RegistrationInputFieldAdapter adapter = new RegistrationInputFieldAdapter(data);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}