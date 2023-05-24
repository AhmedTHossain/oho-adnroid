package com.oho.oho.views.registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oho.oho.R;
import com.oho.oho.adapters.PromptAdapter;
import com.oho.oho.databinding.FragmentFifthProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.interfaces.OnPromptSelectListener;
import com.oho.oho.models.SelectedPrompt;

import java.util.ArrayList;

public class FifthProfileSetup extends Fragment implements OnPromptSelectListener {
    private FragmentFifthProfileSetupBinding binding;
    private OnProfileSetupScreenChange listener;
    private ArrayList<SelectedPrompt> selectedPromptsList = new ArrayList<>();
    private ArrayList<SelectedPrompt> promptsArrayList = new ArrayList<>();
    private PromptAdapter adapter;

    public FifthProfileSetup(OnProfileSetupScreenChange listener) {
        // Required empty public constructor
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFifthProfileSetupBinding.inflate(inflater, container, false);

        setPromptsList();

        binding.buttonNextFifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPromptsList.size()<3)
                    Toast.makeText(requireContext(),"You have to select at least 3 prompts in order to proceed",Toast.LENGTH_LONG).show();
                else {
                    listener.onScreenChange("next", "fifth");
                    saveStringArray(selectedPromptsList);
                }
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onPromptSelect(SelectedPrompt selectedPrompt) {
        if (selectedPromptsList.contains(selectedPrompt))
            selectedPromptsList.remove(selectedPrompt);
        else
            selectedPromptsList.add(selectedPrompt);

        for (SelectedPrompt prompt : promptsArrayList) {
            if (prompt.getPrompt().equals(selectedPrompt.getPrompt())) {
                prompt.setIsSelected(!selectedPrompt.getIsSelected());
            }
        }
        adapter.notifyDataSetChanged();

        String selectedCountText = selectedPromptsList.size() + "/3";

        binding.textSelectionCount.setText(selectedCountText);
    }

    public void setPromptsList() {
        String[] promptList = getResources().getStringArray(R.array.prompt_list);
        for (String prompt : promptList) {
            SelectedPrompt selectedPrompt = new SelectedPrompt(prompt, false);
            promptsArrayList.add(selectedPrompt);
        }

        adapter = new PromptAdapter(requireContext(), promptsArrayList, this);

        binding.recyclerviewPromptQuestions.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewPromptQuestions.setAdapter(adapter);
    }

    private void saveStringArray(ArrayList<SelectedPrompt> stringArray) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(stringArray);
        editor.putString("stringArray", json);
        editor.apply();
    }
}