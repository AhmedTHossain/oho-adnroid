package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class EthnicityInputFragment extends Fragment implements OnInputSelectListener {

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();
    private ArrayList<RegistrationInput> ethnicityArrayList;
    RegistrationInputFieldAdapter adapter;
    private String ethnicityInput="";

    public EthnicityInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_ethnicity_input, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

//        String [] data = {"South Aisan", "East Asian", "African American", "Black", "Latinx", "Pacific Islander", "American Indian", "Hispanic", "White", "Others"};
        String [] data = getResources().getStringArray(R.array.ethnicity_list);

        ethnicityArrayList = new ArrayList<>();
        for (String s: data){
            RegistrationInput input = new RegistrationInput();
            input.setInput(s);
            ethnicityArrayList.add(input);
        }

        adapter = new RegistrationInputFieldAdapter(ethnicityArrayList, this, true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
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
                String ethnicity = profile.getRace();
                if (ethnicity != null) {
                    String[] ethnicityArray = ethnicity.split(",");
                    for (RegistrationInput registrationInput: ethnicityArrayList){
                        for (String str: ethnicityArray){
                            if (registrationInput.getInput().equals(str)){
                                registrationInput.setSelected(true);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        profileData.setRace(ethnicityInput);
        viewModel.saveRegistrationFormData(profileData);
    }

    @Override
    public void onInputSelect(String input) {
        if (ethnicityInput.equals(""))
            ethnicityInput = ethnicityInput + input;
        else
            ethnicityInput = ethnicityInput + "," + input;
    }
}