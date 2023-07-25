package com.oho.oho.views.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.PromptAdapter;
import com.oho.oho.databinding.ActivityAddPromptBinding;
import com.oho.oho.interfaces.OnPromptSelectListener;
import com.oho.oho.models.SelectedPrompt;
import com.oho.oho.viewmodels.AddPromptViewModel;

import java.util.ArrayList;

public class AddPromptActivity extends AppCompatActivity implements OnPromptSelectListener {
    private AddPromptViewModel viewModel;
    private ArrayList<SelectedPrompt> promptsArrayList = new ArrayList<>();
    private PromptAdapter adapter;
    ActivityAddPromptBinding binding;
    private ArrayList<SelectedPrompt> selectedPromptsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPromptBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());
        initViewModels();
        setPromptsList();
//        viewModel.getOnPromptSelect().observe(this, onPromptSelect -> {
//            if (onPromptSelect) {
//                viewModel.getSelectedPrompt().observe(this, selectedPrompt -> {
//                    if (selectedPromptsList.contains(selectedPrompt)) {
//                        selectedPromptsList.remove(selectedPrompt);
//
//                        for (SelectedPrompt prompt : promptsArrayList) {
//                            if (prompt.getPrompt().equals(selectedPrompt.getPrompt())) {
//                                prompt.setIsSelected(!selectedPrompt.getIsSelected());
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
////                       String selectedCountText = selectedPromptsList.size() + "/6";
////                       binding.textSelectionCount.setText(selectedCountText);
//                    }
//                    else {
//
//                        selectedPromptsList.add(selectedPrompt);
//
//                        for (SelectedPrompt prompt : promptsArrayList) {
//                            if (prompt.getPrompt().equals(selectedPrompt.getPrompt())) {
//                                prompt.setIsSelected(!selectedPrompt.getIsSelected());
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
////                           String selectedCountText = selectedPromptsList.size() + "/6";
////                           binding.textSelectionCount.setText(selectedCountText);
//
//                    }
//
//                });
//            }
//        });
    }

    private void initViewModels() {
        viewModel = new ViewModelProvider(this).get(AddPromptViewModel.class);
    }

    public void setPromptsList() {
        String[] promptList = getResources().getStringArray(R.array.prompt_list);
        for (String prompt : promptList) {
            SelectedPrompt selectedPrompt = new SelectedPrompt(prompt, false);
            promptsArrayList.add(selectedPrompt);
        }

        adapter = new PromptAdapter(this, promptsArrayList, this);

        binding.recyclerviewAddPrompt.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewAddPrompt.setAdapter(adapter);
    }

    @Override
    public void onPromptSelect(SelectedPrompt selectedPrompt) {
        if (selectedPromptsList.contains(selectedPrompt)) {
            selectedPromptsList.remove(selectedPrompt);

            for (SelectedPrompt prompt : promptsArrayList) {
                if (prompt.getPrompt().equals(selectedPrompt.getPrompt())) {
                    prompt.setIsSelected(!selectedPrompt.getIsSelected());
                }
            }
        }
        else {

            selectedPromptsList.add(selectedPrompt);

            for (SelectedPrompt prompt : promptsArrayList) {
                if (prompt.getPrompt().equals(selectedPrompt.getPrompt())) {
                    prompt.setIsSelected(!selectedPrompt.getIsSelected());
                }
            }
        }
        adapter.notifyDataSetChanged();
        if (selectedPromptsList.size()>0) {
            binding.textSelectionCount.setVisibility(View.VISIBLE);
            binding.textCount.setText(String.valueOf(selectedPromptsList.size()));
        } else
            binding.textSelectionCount.setVisibility(View.GONE);
    }
}