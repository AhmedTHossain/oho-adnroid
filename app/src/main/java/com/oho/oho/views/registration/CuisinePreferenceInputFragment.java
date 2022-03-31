package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.adapters.RegistrationInputFieldAdapter;
import com.oho.oho.models.Profile;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.viewmodels.RegistrationViewModel;
import com.oho.oho.views.listeners.OnInputDeselectListener;
import com.oho.oho.views.listeners.OnInputSelectListener;

import java.util.ArrayList;

public class CuisinePreferenceInputFragment extends Fragment implements OnInputSelectListener, OnInputDeselectListener {

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();

    private ArrayList<RegistrationInput> cuisineArrayList = new ArrayList<>();
    private String cuisineInput="";
    private RecyclerView recyclerView;
    private RegistrationInputFieldAdapter adapter;

    public CuisinePreferenceInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuisine_preference_input, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        setInitialList(true);

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
//                if (profileData.getPhone() != null){
//                    phoneText.setText(profileData.getPhone());
//                }
            }
        });
        viewModel.getCuisineList().observe(getViewLifecycleOwner(), new Observer<ArrayList<RegistrationInput>>() {
            @Override
            public void onChanged(ArrayList<RegistrationInput> registrationInputs) {
                if (registrationInputs.size() != 0){
                    cuisineArrayList = registrationInputs;
                    setInitialList(false);
                    viewModel.getRegistrationFormData().observe(getViewLifecycleOwner(), new Observer<Profile>() {
                        @Override
                        public void onChanged(Profile profile) {
                            cuisineInput = profile.getCuisine();
                        }
                    });
                }
            }
        });
    }

    public void onPause() {
        super.onPause();
        Log.d("CIF","onPause cuisine = "+cuisineInput);

        cuisineInput = "";
        for (RegistrationInput cuisine: cuisineArrayList)
            if (cuisine.isSelected()) {
                if (cuisineInput.equals(""))
                    cuisineInput = cuisineInput + cuisine.getInput();
                else
                    cuisineInput = cuisineInput + "," + cuisine.getInput();
            }
        profileData.setCuisine(cuisineInput);
        viewModel.saveRegistrationFormData(profileData);

        viewModel.setCuisineList(cuisineArrayList);
    }

    @Override
    public void onInputSelect(String input) {
        Log.d("EIF","onInputSelect called with input = "+input);

        for (RegistrationInput input1: cuisineArrayList){
            if (input1.getInput().equals(input))
                input1.setSelected(true);
        }
    }

    @Override
    public void onInputDeselect(String input) {
        for (RegistrationInput input1: cuisineArrayList){
            if (input1.getInput().equals(input))
                input1.setSelected(false);
        }
    }

    public void setInitialList(boolean firstTimeLoad){

        if (firstTimeLoad) {
            String[] data = getResources().getStringArray(R.array.cuisine_list);

            for (String s : data) {
                RegistrationInput input = new RegistrationInput();
                input.setInput(s);
                cuisineArrayList.add(input);
            }
        }
        adapter = new RegistrationInputFieldAdapter(cuisineArrayList, this, this, true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
    }
}