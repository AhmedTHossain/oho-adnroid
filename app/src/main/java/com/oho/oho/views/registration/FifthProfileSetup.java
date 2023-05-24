package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        binding = FragmentFifthProfileSetupBinding.inflate(inflater,container, false);

        String[] promptList = getResources().getStringArray(R.array.prompt_list);
        for (String prompt: promptList){
            SelectedPrompt selectedPrompt = new SelectedPrompt(prompt,false);
            promptsArrayList.add(selectedPrompt);
        }

        adapter = new PromptAdapter(requireContext(),promptsArrayList,this);

        binding.recyclerviewPromptQuestions.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewPromptQuestions.setAdapter(adapter);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onPromptSelect(SelectedPrompt selectedPrompt) {
        if (selectedPromptsList.contains(selectedPrompt))
            selectedPromptsList.remove(selectedPrompt);
        else
            selectedPromptsList.add(selectedPrompt);

        for (SelectedPrompt prompt: promptsArrayList){
            if (prompt.getPrompt().equals(selectedPrompt.getPrompt())){
                prompt.setIsSelected(!selectedPrompt.getIsSelected());
            }
        }
        adapter.notifyDataSetChanged();

        String selectedCountText = selectedPromptsList.size() +"/3";

        binding.textSelectionCount.setText(selectedCountText);
    }
}