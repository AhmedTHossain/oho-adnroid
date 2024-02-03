package com.oho.oho.views.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityAccountSettingsBinding;
import com.oho.oho.models.NotificationPreference;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.views.LoginActivity;

public class AccountSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAccountSettingsBinding binding;
    private HelperClass helperClass = new HelperClass();
    private NotificationPreference notificationPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_OHO);
        binding = ActivityAccountSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonDeactivateSettings.setOnClickListener(this);
        binding.textEmail.setText(helperClass.getProfile(this).getEmail());

        String userPhone = helperClass.getProfile(this).getPhone();
        String formattedPhoneNumber = userPhone.substring(0, 3) + "-" + userPhone.substring(3, 6) + "-" + userPhone.substring(6);
        binding.textPhone.setText(formattedPhoneNumber);

        notificationPreference = helperClass.getNotificationPreference(this);

        setNotificationPreference();
        setNotificationPreferenceListeners();
    }

    private void setNotificationPreference() {
        Log.d("AccountSettingsActivity","notification preference for user = "+notificationPreference.toJsonString());
        binding.switchMatchNotification.setChecked(notificationPreference.getMatchNotification());
        binding.switchChatNotification.setChecked(notificationPreference.getChatNotification());
        binding.switchLikeNotification.setChecked(notificationPreference.getLikeNotification());
    }

    private void setNotificationPreferenceListeners() {
        binding.switchChatNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationPreference.setChatNotification(true);
                    helperClass.showSnackBar(binding.containermain, "Chat notifications are turned ON successfully!");
                } else {
                    notificationPreference.setChatNotification(false);
                    helperClass.showSnackBar(binding.containermain, "Chat notifications are turned OFF successfully!");
                }
                helperClass.setNotificationPreference(AccountSettingsActivity.this,notificationPreference);
                Log.d("AccountSettingsActivity","notification preference for user = "+notificationPreference.toJsonString());
            }
        });

        binding.switchLikeNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationPreference.setLikeNotification(true);
                    helperClass.showSnackBar(binding.containermain, "Like notifications are turned ON successfully!");
                } else {
                    notificationPreference.setLikeNotification(false);
                    helperClass.showSnackBar(binding.containermain, "Like notifications are turned OFF successfully!");
                }
                helperClass.setNotificationPreference(AccountSettingsActivity.this,notificationPreference);
                Log.d("AccountSettingsActivity","notification preference for user = "+notificationPreference.toJsonString());
            }
        });

        binding.switchMatchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationPreference.setMatchNotification(true);
                    helperClass.showSnackBar(binding.containermain, "Match notifications are turned ON successfully!");
                } else {
                    notificationPreference.setMatchNotification(false);
                    helperClass.showSnackBar(binding.containermain, "Match notifications are turned OFF successfully!");
                }
                helperClass.setNotificationPreference(AccountSettingsActivity.this,notificationPreference);
                Log.d("AccountSettingsActivity","notification preference for user = "+notificationPreference.toJsonString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_deactivate_settings:

                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View promptsView = li.inflate(R.layout.deactivate_profile_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);
                // set alert_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Delete", null)
                        .setNegativeButton("Cancel", null);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        Button buttonNegative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                        buttonNegative.setTextColor(ContextCompat.getColor(AccountSettingsActivity.this, R.color.black));
                        button.setTextColor(ContextCompat.getColor(AccountSettingsActivity.this, R.color.ted_image_picker_primary_pressed));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(AccountSettingsActivity.this, "Your profile will be deactivated shortly..", Toast.LENGTH_SHORT).show();
                                int delay_in_ms = 1000; //1Second interval
                                new Handler().postDelayed(() -> {
                                    startActivity(new Intent(AccountSettingsActivity.this, LoginActivity.class));
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