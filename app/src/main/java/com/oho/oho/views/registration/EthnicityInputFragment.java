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

public class EthnicityInputFragment extends Fragment implements OnInputSelectListener, OnInputDeselectListener {

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();

    private ArrayList<RegistrationInput> ethnicityArrayList = new ArrayList<>();

    RegistrationInputFieldAdapter adapter;
    private String ethnicityInput="";

    private RecyclerView recyclerView;

    public EthnicityInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_ethnicity_input, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        setInitialList(true);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        viewModel.getEthnicityList().observe(getViewLifecycleOwner(), new Observer<ArrayList<RegistrationInput>>() {
            @Override
            public void onChanged(ArrayList<RegistrationInput> registrationInputs) {
                if (registrationInputs.size() != 0){
                   ethnicityArrayList = registrationInputs;
                   setInitialList(false);
                   viewModel.getRegistrationFormData().observe(getViewLifecycleOwner(), new Observer<Profile>() {
                       @Override
                       public void onChanged(Profile profile) {
                           ethnicityInput = profile.getRace();
                       }
                   });
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("EIF","onPause ethnicity = "+ethnicityInput);

        ethnicityInput = "";
        for (RegistrationInput ethnicity: ethnicityArrayList)
            if (ethnicity.isSelected()) {
                if (ethnicityInput.equals(""))
                    ethnicityInput = ethnicityInput + ethnicity.getInput();
                else
                    ethnicityInput = ethnicityInput + "," + ethnicity.getInput();
            }
        profileData.setRace(ethnicityInput);
        viewModel.saveRegistrationFormData(profileData);

        viewModel.setEthnicityList(ethnicityArrayList);
    }

    @Override
    public void onInputSelect(String input) {
        Log.d("EIF","onInputSelect called with input = "+input);

        for (RegistrationInput input1: ethnicityArrayList){
            if (input1.getInput().equals(input))
                input1.setSelected(true);
        }
    }


    @Override
    public void onInputDeselect(String input) {
        Log.d("EIF","onInputDeselect called with input = "+input);

        for (RegistrationInput input1: ethnicityArrayList){
            if (input1.getInput().equals(input))
                input1.setSelected(false);
        }
    }

    public void setInitialList(boolean firstTimeLoad){

        if (firstTimeLoad) {
            String[] data = getResources().getStringArray(R.array.ethnicity_list);

            for (String s : data) {
                RegistrationInput input = new RegistrationInput();
                input.setInput(s);
                ethnicityArrayList.add(input);
            }
        }
        adapter = new RegistrationInputFieldAdapter(ethnicityArrayList, this, this, true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
    }

}