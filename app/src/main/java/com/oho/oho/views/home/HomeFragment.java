package com.oho.oho.views.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.PromptDisplayAdapter;
import com.oho.oho.databinding.FragmentHomeBinding;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.ProfileDisplay;
import com.oho.oho.models.PromptDisplay;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SwipeListener {

    FragmentHomeBinding binding;
    ArrayList<PromptDisplay> promptDisplayArrayList = new ArrayList<>();
    ProfileDisplay profileDisplay;

    String mockProfileDisplayed = "female";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        showMaleProfile();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onSwipe(int swipeDirection, SeekBar seekBar) {
        Log.d("Home", "swipe value = " + swipeDirection);
        if (swipeDirection == -1) {
            vibrate();
            seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar_left));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mockProfileDisplayed.equals("male")) {
                        showFemaleProfile();
                    } else
                        showMaleProfile();
                }
            }, 250);
        } else if (swipeDirection == 1) {
            vibrate();
            seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar_right));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mockProfileDisplayed.equals("male")) {
                        showFemaleProfile();
                    } else
                        showMaleProfile();
                }
            }, 250);
        } else {
            seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar));
//                    Toast.makeText(requireContext(),"Still deciding...",Toast.LENGTH_SHORT).show();
        }
    }

    public void vibrate() {
        // get the VIBRATOR_SERVICE system service
        final Vibrator vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
        // this type of vibration requires API 29
        final VibrationEffect vibrationEffect2;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

            // create vibrator effect with the constant EFFECT_CLICK
            vibrationEffect2 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK);

            // it is safe to cancel other vibrations currently taking place
            vibrator.cancel();

            vibrator.vibrate(vibrationEffect2);
        }
    }

    public void showMaleProfile() {
        mockProfileDisplayed = "male";
        promptDisplayArrayList.clear();
        profileDisplay = new ProfileDisplay(
                "https://drive.google.com/uc?id=1WFQlei8QJG6XEUpND3eNZcuCHA4pOXF_",
                "James B. Marchetti",
                "Honolulu, HI",
                "Application Support Engineer",
                "Male",
                "6'2\"",
                "White",
                "Agnostic",
                "Not Vaccinated",
                "5 km"
        );

        //TODO: for now hard coded data for the profiles' prompt section which will later be replaced with data coming from the APIs from the ViewModel
        PromptDisplay prompt1 = new PromptDisplay(
                "the most adventurous thing I did was...",
                "Sky Diving from 22,000 ft",
                "https://drive.google.com/uc?id=1NOF8cCgbIZOTluaU9WW5OP-HdB2RxRnO",
                "Fools are those that believe smiles are a sign of happiness.");
        PromptDisplay prompt2 = new PromptDisplay(
                "in a partner, I'm looking for...",
                "Humbleness",
                "https://drive.google.com/uc?id=1kXeY2QzqmIV_pTmj4fc5jvq2ZVA-JuWb",
                "There's power in looking silly and not caring that you do."
        );
        PromptDisplay prompt3 = new PromptDisplay(
                "whiskey or wine?",
                "Whiskey!",
                "https://drive.google.com/uc?id=1NFwSfMnJbBe-DWdFcEB1X3SDgtXWNwQo",
                "I mean sure, I have my bad days, but then I remember what a cute smile I have."
        );
        PromptDisplay prompt4 = new PromptDisplay(
                "ideal Sunday for me is...",
                "All day long outdooor activities!",
                "https://drive.google.com/uc?id=1AmdGTggplSNhyCo7B--w9C6feLz3yOIY",
                "Life is short, don't wanna waste any moment to live :)"
        );

        promptDisplayArrayList.add(null);
        promptDisplayArrayList.add(prompt1);
        promptDisplayArrayList.add(prompt2);
        promptDisplayArrayList.add(prompt3);
        promptDisplayArrayList.add(prompt4);
        promptDisplayArrayList.add(null);

        PromptDisplayAdapter adapter = new PromptDisplayAdapter(promptDisplayArrayList, profileDisplay, this, requireContext());
        binding.recyclerviewPromptSection.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewPromptSection.setAdapter(adapter);
    }

    public void showFemaleProfile() {
        mockProfileDisplayed = "female";
        promptDisplayArrayList.clear();
        profileDisplay = new ProfileDisplay(
                "https://drive.google.com/uc?id=1-3SuycWwHPN4Sit63R4um7yK27eBuAFM",
                "Ruby D. Snyder",
                "New York, NY",
                "Interior Designer",
                "Femal",
                "5'10\"",
                "White",
                "Agnostic",
                "Vaccinated",
                "1 km"
        );

        //TODO: for now hard coded data for the profiles' prompt section which will later be replaced with data coming from the APIs from the ViewModel
        PromptDisplay prompt1 = new PromptDisplay(
                "the most adventurous thing I did was...",
                "Sky Diving from 22,000 ft",
                "https://drive.google.com/uc?id=10pQFA_nCvOyFbubW4w4DzsFc16WesrO4",
                "Fools are those that believe smiles are a sign of happiness.");
        PromptDisplay prompt2 = new PromptDisplay(
                "in a partner, I'm looking for...",
                "Humbleness",
                "https://drive.google.com/uc?id=1VmE4VN7AB8DHFUgMyKbsCBsP4vMdY6ce",
                "There's power in looking silly and not caring that you do."
        );
        PromptDisplay prompt0 = new PromptDisplay(
                "in a partner, I'm looking for...",
                "Humbleness",
                "https://drive.google.com/uc?id=1_5UTcqIYhH8BMhQArmJ_uaAVwV75jwkx",
                "There's power in looking silly and not caring that you do."
        );
        PromptDisplay prompt3 = new PromptDisplay(
                "whiskey or wine?",
                "Whiskey!",
                "https://drive.google.com/uc?id=1Ke8W48GImyiLH5rhvO501pkh-GxgDV88",
                "I mean sure, I have my bad days, but then I remember what a cute smile I have."
        );
        PromptDisplay prompt4 = new PromptDisplay(
                "ideal Sunday for me is...",
                "All day long outdooor activities!",
                "https://drive.google.com/uc?id=1EiPKNPIIy7LI1ZcKmLfKZwLaWoyi-6kw",
                "Life is short, don't wanna waste any moment to live :)"
        );

        promptDisplayArrayList.add(null);
        promptDisplayArrayList.add(prompt1);
        promptDisplayArrayList.add(prompt2);
        promptDisplayArrayList.add(prompt0);
        promptDisplayArrayList.add(prompt3);
        promptDisplayArrayList.add(prompt4);
        promptDisplayArrayList.add(null);

        PromptDisplayAdapter adapter = new PromptDisplayAdapter(promptDisplayArrayList, profileDisplay, this, requireContext());
        binding.recyclerviewPromptSection.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewPromptSection.setAdapter(adapter);
    }
}