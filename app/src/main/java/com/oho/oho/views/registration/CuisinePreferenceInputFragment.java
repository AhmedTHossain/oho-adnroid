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
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.views.listeners.OnInputSelectListener;

import java.util.ArrayList;

public class CuisinePreferenceInputFragment extends Fragment implements OnInputSelectListener {

    private ArrayList<RegistrationInput> cuisineArrayList;

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

        cuisineArrayList = new ArrayList<>();
        for (String s: data){
            RegistrationInput input = new RegistrationInput();
            input.setInput(s);
            cuisineArrayList.add(input);
        }

        RegistrationInputFieldAdapter adapter = new RegistrationInputFieldAdapter(cuisineArrayList, this, true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onInputSelect(String input) {

    }
}