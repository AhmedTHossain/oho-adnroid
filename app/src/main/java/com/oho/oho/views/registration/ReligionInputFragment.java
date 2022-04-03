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
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.adapters.RegistrationInputFieldAdapter;
import com.oho.oho.models.Profile;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.viewmodels.RegistrationViewModel;
import com.oho.oho.views.listeners.OnInputDeselectListener;
import com.oho.oho.views.listeners.OnInputSelectListener;

import java.util.ArrayList;

public class ReligionInputFragment extends Fragment implements View.OnClickListener{

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();
    private String religionInput = "";

    private CardView buttonChristian, buttonCatholic, buttonJewish, buttonMuslim, buttonHindu, buttonSikh, buttonAgnostic, buttonBuddhist, buttonSpiritual, buttonOthers;
    private TextView buttonChristianText, buttonCatholicText, buttonJewishText, buttonMuslimText, buttonHinduText, buttonSikhText, buttonAgnosticText, buttonBuddhistText, buttonSpiritualText, buttonOthersText;

    public ReligionInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_religion_input, container, false);

        buttonChristian = view.findViewById(R.id.button_christian);
        buttonCatholic  = view.findViewById(R.id.button_catholic);
        buttonJewish    = view.findViewById(R.id.button_jewish);
        buttonMuslim    = view.findViewById(R.id.button_muslim);
        buttonHindu     = view.findViewById(R.id.button_hindu);
        buttonSikh      = view.findViewById(R.id.button_sikh);
        buttonAgnostic  = view.findViewById(R.id.button_agnostic);
        buttonBuddhist  = view.findViewById(R.id.button_buddhist);
        buttonSpiritual = view.findViewById(R.id.button_spiritual);
        buttonOthers    = view.findViewById(R.id.button_others);

        buttonChristianText = view.findViewById(R.id.text_christian);
        buttonCatholicText  = view.findViewById(R.id.text_catholic);
        buttonJewishText    = view.findViewById(R.id.text_jewish);
        buttonMuslimText    = view.findViewById(R.id.text_muslim);
        buttonHinduText     = view.findViewById(R.id.text_hindu);
        buttonSikhText      = view.findViewById(R.id.text_sikh);
        buttonAgnosticText  = view.findViewById(R.id.text_agnostic);
        buttonBuddhistText  = view.findViewById(R.id.text_buddhist);
        buttonSpiritualText = view.findViewById(R.id.text_spiritual);
        buttonOthersText    = view.findViewById(R.id.text_others);

        buttonChristian.setOnClickListener(this);
        buttonCatholic.setOnClickListener(this);
        buttonJewish.setOnClickListener(this);
        buttonMuslim.setOnClickListener(this);
        buttonHindu.setOnClickListener(this);
        buttonSikh.setOnClickListener(this);
        buttonAgnostic.setOnClickListener(this);
        buttonBuddhist.setOnClickListener(this);
        buttonSpiritual.setOnClickListener(this);
        buttonOthers.setOnClickListener(this);

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
                if (profile.getReligion() != null) {
                    religionInput = profile.getReligion();
                    setSelecetedInput(religionInput);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("CIF","onPause religion = "+ religionInput);

        profileData.setReligion(religionInput);
        viewModel.saveRegistrationFormData(profileData);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_christian)
            onButtonClick(buttonChristianText.getText().toString(), buttonChristianText);
        if (v.getId() == R.id.button_catholic)
            onButtonClick(buttonCatholicText.getText().toString(), buttonCatholicText);
        if (v.getId() == R.id.button_jewish)
            onButtonClick(buttonJewishText.getText().toString(), buttonJewishText);
        if (v.getId() == R.id.button_muslim)
            onButtonClick(buttonMuslimText.getText().toString(), buttonMuslimText);
        if (v.getId() == R.id.button_hindu)
            onButtonClick(buttonHinduText.getText().toString(), buttonHinduText);
        if (v.getId() == R.id.button_sikh)
            onButtonClick(buttonSikhText.getText().toString(), buttonSikhText);
        if (v.getId() == R.id.button_agnostic)
            onButtonClick(buttonAgnosticText.getText().toString(), buttonAgnosticText);
        if (v.getId() == R.id.button_buddhist)
            onButtonClick(buttonBuddhistText.getText().toString(), buttonBuddhistText);
        if (v.getId() == R.id.button_spiritual)
            onButtonClick(buttonSpiritualText.getText().toString(), buttonSpiritualText);
        if (v.getId() == R.id.button_others)
            onButtonClick(buttonOthersText.getText().toString(), buttonOthersText);
    }

    public void selectButton(String button_text, TextView buttonTextView){
        religionInput = button_text;
        buttonTextView.setBackgroundResource(R.drawable.input_selected_background);
        buttonTextView.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
    }

    public void deSelectButton(String button_text, TextView buttonTextView){
        religionInput = "";
        buttonTextView.setBackgroundResource(R.color.white);
        buttonTextView.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
    }

    public void onButtonClick(String button_text, TextView buttonTextView){
        if (religionInput.equals(""))
            selectButton(button_text, buttonTextView);
        else {
            if (religionInput.equals(button_text))
                deSelectButton(button_text, buttonTextView);
            else
                Toast.makeText(requireContext(), "Can select only one religion", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSelecetedInput(String religionInput) {
        if (religionInput.equals(buttonChristianText.getText().toString()))
            selectButton(buttonChristianText.getText().toString(),buttonChristianText);
        if (religionInput.equals(buttonCatholicText.getText().toString()))
            selectButton(buttonCatholicText.getText().toString(),buttonCatholicText);
        if (religionInput.equals(buttonJewishText.getText().toString()))
            selectButton(buttonJewishText.getText().toString(),buttonJewishText);
        if (religionInput.equals(buttonMuslimText.getText().toString()))
            selectButton(buttonMuslimText.getText().toString(),buttonMuslimText);
        if (religionInput.equals(buttonHinduText.getText().toString()))
            selectButton(buttonHinduText.getText().toString(),buttonHinduText);
        if (religionInput.equals(buttonSikhText.getText().toString()))
            selectButton(buttonSikhText.getText().toString(),buttonSikhText);
        if (religionInput.equals(buttonAgnosticText.getText().toString()))
            selectButton(buttonAgnosticText.getText().toString(),buttonAgnosticText);
        if (religionInput.equals(buttonBuddhistText.getText().toString()))
            selectButton(buttonBuddhistText.getText().toString(),buttonBuddhistText);
        if (religionInput.equals(buttonSpiritualText.getText().toString()))
            selectButton(buttonSpiritualText.getText().toString(),buttonSpiritualText);
        if (religionInput.equals(buttonOthersText.getText().toString()))
            selectButton(buttonOthersText.getText().toString(),buttonOthersText);
    }
}