package com.oho.oho.views.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oho.oho.R;
import com.oho.oho.adapters.RegistrationInputFieldAdapter;
import com.oho.oho.models.Profile;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.viewmodels.RegistrationViewModel;
import com.oho.oho.views.listeners.OnInputDeselectListener;
import com.oho.oho.views.listeners.OnInputSelectListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CuisinePreferenceInputFragment extends Fragment implements View.OnClickListener{

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();
    private String cuisineInput="";
    private ArrayList<String> cuisineInputArrayList = new ArrayList<>();

    private CardView
            buttonItalian,
            buttonJapanese,
            buttonIndian,
            buttonMediterranean,
            buttonKorean,
            buttonAmerican,
            buttonVegetarian,
            buttonVietnamese,
            buttonChinese,
            buttonOpenToAll,
            buttonOthers,
            buttonHealthy;

    private TextView
            buttonItalianText,
            buttonJapaneseText,
            buttonIndianText,
            buttonMediterraneanText,
            buttonKoreanText,
            buttonAmericanText,
            buttonVegetarianText,
            buttonVietnameseText,
            buttonChineseText,
            buttonOpenToAllText,
            buttonOthersText,
            buttonHealthyText;

    public CuisinePreferenceInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuisine_preference_input, container, false);

        buttonItalianText = view.findViewById(R.id.text_italian);
        buttonJapaneseText = view.findViewById(R.id.text_japanese);
        buttonIndianText = view.findViewById(R.id.text_indian);
        buttonMediterraneanText = view.findViewById(R.id.text_mediterranean);
        buttonKoreanText = view.findViewById(R.id.text_korean);
        buttonAmericanText = view.findViewById(R.id.text_american);
        buttonVegetarianText = view.findViewById(R.id.text_vegetarian);
        buttonVietnameseText = view.findViewById(R.id.text_vietnamese);
        buttonChineseText = view.findViewById(R.id.text_chinese);
        buttonOpenToAllText = view.findViewById(R.id.text_open_to_all);
        buttonOthersText = view.findViewById(R.id.text_others);
        buttonHealthyText  = view.findViewById(R.id.text_healthy);

        buttonItalian = view.findViewById(R.id.button_italian);
        buttonJapanese = view.findViewById(R.id.button_japanese);
        buttonIndian = view.findViewById(R.id.button_indian);
        buttonMediterranean = view.findViewById(R.id.button_mediterranean);
        buttonKorean = view.findViewById(R.id.button_korean);
        buttonAmerican = view.findViewById(R.id.button_american);
        buttonVegetarian = view.findViewById(R.id.button_vegetarian);
        buttonVietnamese = view.findViewById(R.id.button_vietnamese);
        buttonChinese = view.findViewById(R.id.button_chinese);
        buttonOpenToAll = view.findViewById(R.id.button_open_to_all);
        buttonOthers = view.findViewById(R.id.button_others);
        buttonHealthy = view.findViewById(R.id.button_healthy);

        buttonItalian.setOnClickListener(this);
        buttonJapanese.setOnClickListener(this);
        buttonIndian.setOnClickListener(this);
        buttonMediterranean.setOnClickListener(this);
        buttonKorean.setOnClickListener(this);
        buttonAmerican.setOnClickListener(this);
        buttonVegetarian.setOnClickListener(this);
        buttonVietnamese.setOnClickListener(this);
        buttonChinese.setOnClickListener(this);
        buttonOpenToAll.setOnClickListener(this);
        buttonOthers.setOnClickListener(this);
        buttonHealthy.setOnClickListener(this);

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
                cuisineInput = profile.getCuisine();

                if (cuisineInput != null)
                    setSelectedInput(cuisineInput);
                Log.d("EIF","onViewCreated cuisine = " + cuisineInput);
            }
        });
    }

    public void onPause() {
        super.onPause();

        Set<String> set = new HashSet<>(cuisineInputArrayList);
        cuisineInputArrayList.clear();
        cuisineInputArrayList.addAll(set);

        cuisineInput = "";
        cuisineInput = String.join(",",cuisineInputArrayList);

        Log.d("EIF","onPause cuisine = "+cuisineInput);

        profileData.setCuisine(cuisineInput);
        viewModel.saveRegistrationFormData(profileData);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_italian)
            onButtonClick(buttonItalianText.getText().toString(),buttonItalianText);
        if (v.getId() == R.id.button_japanese)
            onButtonClick(buttonJapaneseText.getText().toString(),buttonJapaneseText);
        if (v.getId() == R.id.button_indian)
            onButtonClick(buttonIndianText.getText().toString(),buttonIndianText);
        if (v.getId() == R.id.button_mediterranean)
            onButtonClick(buttonMediterraneanText.getText().toString(),buttonMediterraneanText);
        if (v.getId() == R.id.button_korean)
            onButtonClick(buttonKoreanText.getText().toString(),buttonKoreanText);
        if (v.getId() == R.id.button_american)
            onButtonClick(buttonAmericanText.getText().toString(),buttonAmericanText);
        if (v.getId() == R.id.button_vegetarian)
            onButtonClick(buttonVegetarianText.getText().toString(),buttonVegetarianText);
        if (v.getId() == R.id.button_vietnamese)
            onButtonClick(buttonVietnameseText.getText().toString(),buttonVietnameseText);
        if (v.getId() == R.id.button_chinese)
            onButtonClick(buttonChineseText.getText().toString(),buttonChineseText);
        if (v.getId() == R.id.button_open_to_all)
            onButtonClick(buttonOpenToAllText.getText().toString(),buttonOpenToAllText);
        if (v.getId() == R.id.button_others)
            onButtonClick(buttonOpenToAllText.getText().toString(),buttonOpenToAllText);
        if (v.getId() == R.id.button_healthy)
            onButtonClick(buttonHealthyText.getText().toString(),buttonHealthyText);
    }

    public void selectButton(String button_text, TextView buttonTextView){
        cuisineInputArrayList.add(button_text);

        buttonTextView.setBackgroundResource(R.drawable.input_selected_background);
        buttonTextView.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
    }

    public void deSelectButton(String button_text, TextView buttonTextView){
        cuisineInputArrayList.remove(button_text);

        buttonTextView.setBackgroundResource(R.color.white);
        buttonTextView.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
    }

    public void onButtonClick(String button_text, TextView buttonTextView){
        if (cuisineInputArrayList.size() == 0)
            selectButton(button_text, buttonTextView);
        else {
            if (cuisineInputArrayList.contains(button_text))
                deSelectButton(button_text, buttonTextView);
            else
                selectButton(button_text, buttonTextView);
        }
    }

    private void setSelectedInput(String cuisineInput) {
        String [] arr = cuisineInput.split(",");
        for (String cuisine: arr){
            if (cuisine.equals(buttonItalianText.getText().toString())){
                selectButton(buttonItalianText.getText().toString(),buttonItalianText);
            }
            if (cuisine.equals(buttonJapaneseText.getText().toString())){
                selectButton(buttonJapaneseText.getText().toString(),buttonJapaneseText);
            }
            if (cuisine.equals(buttonIndianText.getText().toString())){
                selectButton(buttonIndianText.getText().toString(),buttonIndianText);
            }
            if (cuisine.equals(buttonMediterraneanText.getText().toString())){
                selectButton(buttonMediterraneanText.getText().toString(),buttonMediterraneanText);
            }
            if (cuisine.equals(buttonKoreanText.getText().toString())){
                selectButton(buttonKoreanText.getText().toString(),buttonKoreanText);
            }
            if (cuisine.equals(buttonAmericanText.getText().toString())){
                selectButton(buttonAmericanText.getText().toString(),buttonAmericanText);
            }
            if (cuisine.equals(buttonVegetarianText.getText().toString())){
                selectButton(buttonVegetarianText.getText().toString(),buttonVegetarianText);
            }
            if (cuisine.equals(buttonVietnameseText.getText().toString())){
                selectButton(buttonVietnameseText.getText().toString(),buttonVietnameseText);
            }
            if (cuisine.equals(buttonChineseText.getText().toString())){
                selectButton(buttonChineseText.getText().toString(),buttonChineseText);
            }
            if (cuisine.equals(buttonOpenToAllText.getText().toString())){
                selectButton(buttonOpenToAllText.getText().toString(),buttonOpenToAllText);
            }
            if (cuisine.equals(buttonOthersText.getText().toString())){
                selectButton(buttonOthersText.getText().toString(),buttonOthersText);
            }
            if (cuisine.equals(buttonHealthyText.getText().toString())){
                selectButton(buttonHealthyText.getText().toString(),buttonHealthyText);
            }
        }
    }

}