package com.oho.oho.views.registration;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.RadioListItemAdapter;
import com.oho.oho.databinding.ActivityRegistrationBinding;
import com.oho.oho.interfaces.RadioListItemClickListener;
import com.oho.oho.viewmodels.RegistrationViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity
        implements View.OnClickListener, RadioListItemClickListener {

    private ActivityRegistrationBinding binding;
    private RegistrationViewModel registrationViewModel;
    private RadioListItemAdapter cuisineAdapter;
    private LinearLayoutManager layoutManager;
    private int feetInput = 0;
    private int inchInput = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        initAuthViewModel();

        binding.textviewHeight.setOnClickListener(this);
        binding.textviewDateOfBirth.setOnClickListener(this);

        String[] resArray = getResources().getStringArray(R.array.cuisine_list);
        List<String> radioItemList = Arrays.asList(resArray);
        ArrayList<String> radioItemArrayList = new ArrayList<>(radioItemList);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        cuisineAdapter = new RadioListItemAdapter(radioItemArrayList,this,this);
        binding.recyclerviewCuisine.setLayoutManager(layoutManager);
        binding.recyclerviewCuisine.setAdapter(cuisineAdapter);

    }

    private void initAuthViewModel() {
        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
    }

    private void startRegistration() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void radioListItemClick(int position) {

    }
}