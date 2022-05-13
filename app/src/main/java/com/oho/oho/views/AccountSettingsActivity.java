package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityAccountSettingsBinding;
import com.oho.oho.databinding.ActivityMainBinding;

public class AccountSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAccountSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_OHO);
        binding = ActivityAccountSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonDeactivateSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_deactivate_settings:

                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View promptsView = li.inflate(R.layout.deactivate_profile_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);
                // set alert_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("CONFIRM",null)
                        .setNegativeButton("CANCEL", null);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(AccountSettingsActivity.this,"Your profile will be deactivated shortly..",Toast.LENGTH_SHORT).show();
                                int delay_in_ms = 1000; //1Second interval
                                new Handler().postDelayed(() -> {
                                    startActivity(new Intent(AccountSettingsActivity.this,LoginActivity.class));
                                    finish();
                                }, delay_in_ms);
                            }
                        });
                    }
                });
                // show it
                alertDialog.show();
                break;
        }
    }
}