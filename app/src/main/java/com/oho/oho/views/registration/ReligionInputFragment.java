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

public class ReligionInputFragment extends Fragment implements OnInputSelectListener, OnInputDeselectListener {

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();
    private ArrayList<RegistrationInput> religionArrayList = new ArrayList<>();
    private String religionInput;
    private RecyclerView recyclerView;
    private RegistrationInputFieldAdapter adapter;

    public ReligionInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_religion_input, container, false);

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
            }
        });
        viewModel.getReligionList().observe(getViewLifecycleOwner(), new Observer<ArrayList<RegistrationInput>>() {
            @Override
            public void onChanged(ArrayList<RegistrationInput> registrationInputs) {
                if (registrationInputs.size() != 0){
                    religionArrayList = registrationInputs;
                    setInitialList(false);
                    viewModel.getRegistrationFormData().observe(getViewLifecycleOwner(), new Observer<Profile>() {
                        @Override
                        public void onChanged(Profile profile) {
                            religionInput = profile.getReligion();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("CIF","onPause religion = "+profileData.getReligion());

        profileData.setReligion(profileData.getReligion());
        viewModel.saveRegistrationFormData(profileData);
//
        viewModel.setReligionList(religionArrayList);
    }

    @Override
    public void onInputSelect(String input) {

        religionInput = input;

        for (RegistrationInput religion: religionArrayList)
            if (religion.getInput().equals(religionInput))
                profileData.setReligion(religionInput);

        Log.d("EIF","onInputSelect called with input = "+religionInput);
    }

    @Override
    public void onInputDeselect(String input) {
        Log.d("EIF","onInputDeSelect called with input = "+input);
        religionInput = null;

        for (RegistrationInput religion: religionArrayList)
            if (religion.getInput().equals(religionInput))
                religion.setSelected(false);
    }

    public void setInitialList(boolean firstTimeLoad){

        if (firstTimeLoad) {
            String[] data = getResources().getStringArray(R.array.religion_list);

            for (String s : data) {
                RegistrationInput input = new RegistrationInput();
                input.setInput(s);
                religionArrayList.add(input);
            }
        }
        adapter = new RegistrationInputFieldAdapter(requireContext(), religionArrayList, this, this, false);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
    }
}