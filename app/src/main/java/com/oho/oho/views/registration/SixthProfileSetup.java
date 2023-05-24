package com.oho.oho.views.registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oho.oho.R;
import com.oho.oho.adapters.SelectedPromptAdapter;
import com.oho.oho.databinding.FragmentFifthProfileSetupBinding;
import com.oho.oho.databinding.FragmentSixthProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.models.SelectedPrompt;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SixthProfileSetup extends Fragment {
    FragmentSixthProfileSetupBinding binding;
    SharedPreferences sharedPreferences;
    OnProfileSetupScreenChange listener;

    public SixthProfileSetup(OnProfileSetupScreenChange listener) {
        // Required empty public constructor
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSixthProfileSetupBinding.inflate(inflater, container, false);

        ArrayList<SelectedPrompt> selectedPrompts = retrieveStringArray();


        SelectedPromptAdapter adapter = new SelectedPromptAdapter(selectedPrompts, requireContext());

        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setClipToPadding(false);
        binding.viewpager.setClipChildren(false);
        binding.viewpager.setUserInputEnabled(false);
        binding.viewpager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        binding.buttonNextSixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem()+1);
            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private ArrayList<SelectedPrompt> retrieveStringArray() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("stringArray", null);
        Type type = new TypeToken<ArrayList<SelectedPrompt>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }
}