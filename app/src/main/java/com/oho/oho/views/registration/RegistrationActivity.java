package com.oho.oho.views.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.oho.oho.R;
import com.oho.oho.adapters.RegistrationAdapter;
import com.oho.oho.views.animations.ZoomOutPageTransformer;

public class RegistrationActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private RegistrationAdapter adapter;
    private CardView buttonNext, buttonPrevious, buttonStart, buttonComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        viewPager2 = findViewById(R.id.viewpager_registration);
        tabLayout  = findViewById(R.id.tab_indicator_registration);
        buttonNext = findViewById(R.id.button_next_registration);
        buttonPrevious = findViewById(R.id.button_prev_registration);
        buttonStart = findViewById(R.id.button_start_registration);
        buttonComplete = findViewById(R.id.button_complete_registration);

        //Setting up Animations
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        //Set button alpha to zero
        buttonStart.setAlpha(0f);
        buttonComplete.setAlpha(0f);
        buttonNext.setAlpha(0f);
        buttonPrevious.setAlpha(0f);
        //Animate the alpha value to 1f and set duration as 1.5 secs.
        buttonStart.animate().alpha(1f).setDuration(1000);
        buttonComplete.animate().alpha(1f).setDuration(1000);
        buttonNext.animate().alpha(1f).setDuration(1000);
        buttonPrevious.animate().alpha(1f).setDuration(1000);

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new RegistrationAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(adapter);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(1);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()-1);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    buttonStart.setVisibility(View.VISIBLE);
                    buttonComplete.setVisibility(View.GONE);
                    buttonNext.setVisibility(View.GONE);
                    buttonPrevious.setVisibility(View.GONE);
                } else if (position == 10){
                    buttonStart.setVisibility(View.GONE);
                    buttonComplete.setVisibility(View.VISIBLE);
                    buttonNext.setVisibility(View.GONE);
                    buttonPrevious.setVisibility(View.GONE);
                } else {
                    buttonStart.setVisibility(View.GONE);
                    buttonComplete.setVisibility(View.GONE);
                    buttonNext.setVisibility(View.VISIBLE);
                    buttonPrevious.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}