package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.oho.oho.R;
import com.oho.oho.adapters.OnboardingAdapter;
import com.oho.oho.databinding.ActivityOnboardingBinding;
import com.oho.oho.databinding.ActivityRegistrationBinding;
import com.oho.oho.models.OnboardingItem;
import com.oho.oho.views.animations.ZoomOutPageTransformer;
import com.oho.oho.views.registration.ProfileSetupActivity;
import com.oho.oho.views.registration.RegistrationActivity;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private static final String TAG = "OnboardingActivity";
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicator;
    private MaterialButton buttonOnboardingAction;
    private String onBoardingUserName, onBoardingUserPhone, onBoardingUserEmail;
    private ActivityOnboardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        layoutOnboardingIndicator = findViewById(R.id.layout_indicators);
        buttonOnboardingAction    = findViewById(R.id.button_action);

        onBoardingUserName = getIntent().getStringExtra("name");
        onBoardingUserEmail = getIntent().getStringExtra("email");
        onBoardingUserPhone = getIntent().getStringExtra("phone");

        Log.d(TAG,"name of google logged in user = "+onBoardingUserName);

        setOnboardingItem();

        ViewPager2 viewPager = findViewById(R.id.viewpager);

        //Setting up Animations
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        viewPager.setAdapter(onboardingAdapter);

        setIndicator();
        setCurrentIndicator(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ProfileSetupActivity.class);
                    intent.putExtra("name",onBoardingUserName);
                    intent.putExtra("email",onBoardingUserEmail);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setIndicator() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0,0,30,0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int index){
        int childCount = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if (i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active_two));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive_two));
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1){
            buttonOnboardingAction.setText("Get Started");
        } else {
            buttonOnboardingAction.setText("Next");
            buttonOnboardingAction.setBackgroundColor(ContextCompat.getColor(
                    getApplicationContext(), R.color.ted_image_picker_primary_pressed
            ));
        }
    }

    private void setOnboardingItem(){
        List<OnboardingItem> onboardingItemList = new ArrayList<>();

        OnboardingItem itemSearching = new OnboardingItem();
        itemSearching.setTitle("Start  Searching...");
        itemSearching.setDescription("When you’re ready to go out during the week, let the app know, and we’ll match you up!");
        itemSearching.setImage(R.drawable.searching);

        OnboardingItem itemMatching = new OnboardingItem();
        itemMatching.setTitle("Match!");
        itemMatching.setDescription("When matched, the app will schedule a time to meet for lunch or dinner!");
        itemMatching.setImage(R.drawable.matching);

        OnboardingItem itemMeeting = new OnboardingItem();
        itemMeeting.setTitle("Meet up!");
        itemMeeting.setDescription("Meet up with your date and get to know each other!");
        itemMeeting.setImage(R.drawable.meetup);

        onboardingItemList.add(itemSearching);
        onboardingItemList.add(itemMatching);
        onboardingItemList.add(itemMeeting);

        onboardingAdapter = new OnboardingAdapter(onboardingItemList,getApplicationContext());
    }
}