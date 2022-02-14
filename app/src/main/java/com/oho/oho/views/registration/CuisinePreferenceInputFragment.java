package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.adapters.RegistrationInputFieldAdapter;

public class CuisinePreferenceInputFragment extends Fragment {

    public CuisinePreferenceInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuisine_preference_input, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

//        String [] data = {"Vegan", "Vegetarian", "Italian", "American", "Chinese", "Japanese", "Vietnamese", "Mediterranean", "Healthy", "Pescatarian", "Others"};
        String [] data = getResources().getStringArray(R.array.cuisine_list);
        RegistrationInputFieldAdapter adapter = new RegistrationInputFieldAdapter(data);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}