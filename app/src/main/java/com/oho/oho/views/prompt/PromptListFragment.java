package com.oho.oho.views.prompt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.adapters.PromptInputAdapter;
import com.oho.oho.models.Prompt;
import com.oho.oho.viewmodels.PromptViewModel;

import java.util.ArrayList;

public class PromptListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PromptInputAdapter adapter;
    private PromptViewModel promptViewModel;
    private ArrayList<Prompt> promptArrayList = new ArrayList<>();


    public PromptListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prompt_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_prompt_section);

        initPromptViewModel();

        SharedPreferences sharedPref = requireContext().getSharedPreferences("prompt_selected", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", 0);
        editor.putString("question",null);
        editor.apply();

        promptViewModel.getAllPromptList();
        promptViewModel.promptList.observe(getViewLifecycleOwner(), prompts -> {
            promptArrayList.addAll(prompts);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new PromptInputAdapter(getContext(), promptArrayList);

            recyclerView.setAdapter(adapter);
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initPromptViewModel(){
        promptViewModel = new ViewModelProvider(this).get(PromptViewModel.class);
    }
}