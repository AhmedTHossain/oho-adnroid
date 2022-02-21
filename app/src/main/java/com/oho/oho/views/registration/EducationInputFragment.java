package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.adapters.RegistrationInputFieldAdapter;
import com.oho.oho.models.Profile;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.viewmodels.RegistrationViewModel;
import com.oho.oho.views.listeners.OnInputSelectListener;

import java.util.ArrayList;

public class EducationInputFragment extends Fragment implements OnInputSelectListener {

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();
    private String educationInput="";

    private ArrayList<RegistrationInput> educationArrayList;

    public EducationInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education_input, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

//        String [] data = {"Some college", "Associate’s Degree", "Bachelor’s Degree", "Graduate Degree", "Professional Degree", "No Degree"};
        String [] data = getResources().getStringArray(R.array.education_list);

        educationArrayList = new ArrayList<>();

        for (String s: data){
            RegistrationInput input = new RegistrationInput();
            input.setInput(s);
            educationArrayList.add(input);
        }

        RegistrationInputFieldAdapter adapter = new RegistrationInputFieldAdapter(educationArrayList, this, false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        viewModel.getRegistrationFormData().observe(getViewLifecycleOwner(), new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                profileData = profile;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!educationInput.equals("")) {
            profileData.setEducation(educationInput);
            viewModel.saveRegistrationFormData(profileData);
        }
    }

    @Override
    public void onInputSelect(String input) {
        educationInput = input;
    }
}