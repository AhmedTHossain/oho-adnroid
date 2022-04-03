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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EthnicityInputFragment extends Fragment implements View.OnClickListener{

    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();
    private String ethnicityInput="";
    private ArrayList<String> ethnicityInputArrayList = new ArrayList<>();

    private CardView buttonSouthAsian, buttonEastAsian, buttonAfricanAmerican, buttonBlack, buttonLatinx, buttonPacificIslander, buttonAmericanIndian, buttonHispanic, buttonWhite, buttonOthers;
    private TextView buttonSouthAsianText, buttonEastAsianText, buttonAfricanAmericanText, buttonBlackText, buttonLatinxText, buttonPacificIslanderText, buttonAmericanIndianText, buttonHispanicText, buttonWhiteText, buttonOthersText;

    public EthnicityInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_ethnicity_input, container, false);

        buttonSouthAsian      = view.findViewById(R.id.button_south_asian);
        buttonEastAsian       = view.findViewById(R.id.button_east_asian);
        buttonAfricanAmerican = view.findViewById(R.id.button_african_american);
        buttonBlack           = view.findViewById(R.id.button_black);
        buttonLatinx          = view.findViewById(R.id.button_latinx);
        buttonPacificIslander = view.findViewById(R.id.button_pacific_islander);
        buttonAmericanIndian  = view.findViewById(R.id.button_american_indian);
        buttonHispanic        = view.findViewById(R.id.button_hispanic);
        buttonWhite           = view.findViewById(R.id.button_white);
        buttonOthers          = view.findViewById(R.id.button_others);

        buttonSouthAsianText      = view.findViewById(R.id.text_south_asian);
        buttonEastAsianText       = view.findViewById(R.id.text_east_asian);
        buttonAfricanAmericanText = view.findViewById(R.id.text_african_american);
        buttonBlackText           = view.findViewById(R.id.text_black);
        buttonLatinxText          = view.findViewById(R.id.text_latinx);
        buttonPacificIslanderText = view.findViewById(R.id.text_pacific_islander);
        buttonAmericanIndianText  = view.findViewById(R.id.text_american_indian);
        buttonHispanicText        = view.findViewById(R.id.text_hispanic);
        buttonWhiteText           = view.findViewById(R.id.text_white);
        buttonOthersText          = view.findViewById(R.id.text_others);

        buttonSouthAsian.setOnClickListener(this);
        buttonEastAsian.setOnClickListener(this);
        buttonAfricanAmerican.setOnClickListener(this);
        buttonBlack.setOnClickListener(this);
        buttonLatinx.setOnClickListener(this);
        buttonPacificIslander.setOnClickListener(this);
        buttonAmericanIndian.setOnClickListener(this);
        buttonHispanic.setOnClickListener(this);
        buttonWhite.setOnClickListener(this);
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
                ethnicityInput = profile.getRace();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        if(ethnicityInputArrayList.size() > 0){
            ethnicityInput = String.join(",",ethnicityInputArrayList);
        }

        Log.d("EIF","onPause ethnicity = "+ethnicityInput);

        profileData.setRace(ethnicityInput);
        viewModel.saveRegistrationFormData(profileData);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_south_asian)
            onButtonClick(buttonSouthAsianText.getText().toString(),buttonSouthAsianText);
        if (v.getId() == R.id.button_east_asian)
            onButtonClick(buttonEastAsianText.getText().toString(),buttonEastAsianText);
        if (v.getId() == R.id.button_african_american)
            onButtonClick(buttonAfricanAmericanText.getText().toString(),buttonAfricanAmericanText);
        if (v.getId() == R.id.button_black)
            onButtonClick(buttonBlackText.getText().toString(),buttonBlackText);
        if (v.getId() == R.id.button_latinx)
            onButtonClick(buttonLatinxText.getText().toString(),buttonLatinxText);
        if (v.getId() == R.id.button_pacific_islander)
            onButtonClick(buttonPacificIslanderText.getText().toString(),buttonPacificIslanderText);
        if (v.getId() == R.id.button_american_indian)
            onButtonClick(buttonAmericanIndianText.getText().toString(),buttonAmericanIndianText);
        if (v.getId() == R.id.button_hispanic)
            onButtonClick(buttonHispanicText.getText().toString(),buttonHispanicText);
        if (v.getId() == R.id.button_white)
            onButtonClick(buttonWhiteText.getText().toString(),buttonWhiteText);
        if (v.getId() == R.id.button_others)
            onButtonClick(buttonOthersText.getText().toString(),buttonOthersText);
    }

    public void selectButton(String button_text, TextView buttonTextView){
        ethnicityInputArrayList.add(button_text);

        buttonTextView.setBackgroundResource(R.drawable.input_selected_background);
        buttonTextView.setTextColor(getResources().getColor(R.color.white, requireActivity().getTheme()));
    }

    public void deSelectButton(String button_text, TextView buttonTextView){
        ethnicityInputArrayList.remove(button_text);

        buttonTextView.setBackgroundResource(R.color.white);
        buttonTextView.setTextColor(getResources().getColor(R.color.indicatioractive, requireActivity().getTheme()));
    }

    public void onButtonClick(String button_text, TextView buttonTextView){
        if (ethnicityInputArrayList.size() == 0)
            selectButton(button_text, buttonTextView);
        else {
            if (ethnicityInputArrayList.contains(button_text))
                deSelectButton(button_text, buttonTextView);
            else
                selectButton(button_text, buttonTextView);
        }
    }
}