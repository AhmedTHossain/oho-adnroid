package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityFaqactivityBinding;
import com.oho.oho.databinding.ActivityMainBinding;

public class FAQActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityFaqactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityFaqactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonWhat.setOnClickListener(this);
        binding.buttonHow.setOnClickListener(this);
        binding.buttonNotShowup.setOnClickListener(this);
        binding.buttonSafe.setOnClickListener(this);
        binding.buttonMotivation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_what:
                if (v.getTag().equals("collapsed"))
                    expandFaq(binding.textWhatExpand, binding.buttonCollapseWhat, binding.buttonExpandWhat, binding.buttonWhat);
                else
                    collapseFaq(binding.textWhatExpand, binding.buttonCollapseWhat, binding.buttonExpandWhat, binding.buttonWhat);
                break;
            case R.id.button_how:
                if (v.getTag().equals("collapsed"))
                    expandFaq(binding.textHowExpand, binding.buttonCollapseHow, binding.buttonExpandHow, binding.buttonHow);
                else
                    collapseFaq(binding.textHowExpand, binding.buttonCollapseHow, binding.buttonExpandHow, binding.buttonHow);
                break;
            case R.id.button_not_showup:
                if (v.getTag().equals("collapsed"))
                    expandFaq(binding.textNotShowupExpand, binding.buttonCollapseNotShowup, binding.buttonExpandNotShowup, binding.buttonNotShowup);
                else
                    collapseFaq(binding.textNotShowupExpand, binding.buttonCollapseNotShowup, binding.buttonExpandNotShowup, binding.buttonNotShowup);
                break;
            case R.id.button_safe:
                if (v.getTag().equals("collapsed"))
                    expandFaq(binding.textSafeExpand, binding.buttonCollapseSafe, binding.buttonExpandSafe, binding.buttonSafe);
                else
                    collapseFaq(binding.textSafeExpand, binding.buttonCollapseSafe, binding.buttonExpandSafe, binding.buttonSafe);
                break;
            case R.id.button_motivation:
                if (v.getTag().equals("collapsed"))
                    expandFaq(binding.textMotivationExpand, binding.buttonCollapseMotivation, binding.buttonExpandMotivation, binding.buttonMotivation);
                else
                    collapseFaq(binding.textMotivationExpand, binding.buttonCollapseMotivation, binding.buttonExpandMotivation, binding.buttonMotivation);
                break;
        }
    }

    private void expandFaq(TextView answerText, ImageView collapseIcon, ImageView expandIcon, RelativeLayout faqButton){
        answerText.setVisibility(View.VISIBLE);
        collapseIcon.setVisibility(View.GONE);
        expandIcon.setVisibility(View.VISIBLE);
        faqButton.setTag("expanded");
    }

    private void collapseFaq(TextView answerText, ImageView collapseIcon, ImageView expandIcon, RelativeLayout faqButton){
        answerText.setVisibility(View.GONE);
        collapseIcon.setVisibility(View.VISIBLE);
        expandIcon.setVisibility(View.GONE);
        faqButton.setTag("collapsed");
    }
}