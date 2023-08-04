package com.oho.oho.views.settings;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.FaqAdapter;
import com.oho.oho.databinding.ActivityFaqactivityBinding;
import com.oho.oho.models.FAQ;
import com.oho.oho.viewmodels.FaqViewModel;

import java.util.ArrayList;

public class FAQActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityFaqactivityBinding binding;
    private ArrayList<FAQ> faqArrayList = new ArrayList<>();
    private FaqAdapter adapter;
    private FaqViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityFaqactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        expandFaqAnswer();

        String[] faqQuesList = getResources().getStringArray(R.array.faq_questions_list);
        String[] faqAnswerList = getResources().getStringArray(R.array.faq_answers_list);
        for (int i = 0; i < faqQuesList.length; i++) {
            FAQ faq = new FAQ(faqQuesList[i], faqAnswerList[i]);
            if (i == 0)
                faq.setExpanded(true);
            else
                faq.setExpanded(false);
            faqArrayList.add(faq);
        }

        adapter = new FaqAdapter(faqArrayList, this, viewModel);
        binding.recyclerviewFaq.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewFaq.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(FaqViewModel.class);
    }

    private void expandFaqAnswer() {
        viewModel.getFaqTapped().observe(this, faqTapped -> {
            boolean expanded = !faqArrayList.get(faqTapped).getExpanded();
            faqArrayList.get(faqTapped).setExpanded(expanded);
            adapter.notifyDataSetChanged();
        });
    }
}