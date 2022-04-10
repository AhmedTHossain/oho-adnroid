package com.oho.oho.views.prompt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.adapters.PromptInputAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PromptListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PromptInputAdapter adapter;

    public PromptListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prompt_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        List<String> prompts = Arrays.asList(getResources().getStringArray(R.array.prompt_list));
        ArrayList<String> promptArrayList = new ArrayList<>(prompts);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PromptInputAdapter(getContext(), promptArrayList);

        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}