package com.oho.oho.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.responses.VerifyEmailResponse;
import com.oho.oho.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getVerifyEmailResponseObserver().observe(this, new Observer<VerifyEmailResponse>() {
            @Override
            public void onChanged(VerifyEmailResponse verifyEmailResponse) {
                if (verifyEmailResponse != null){
                    Toast.makeText(LoginActivity.this, "Welcome back "+verifyEmailResponse.getProfile().getName()+" !", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Please complete your registration first!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                    finish();
                }
            }
        });

//        viewModel.verifyEmailAPICall("tanzeerhossain@gmail.com");
    }
}