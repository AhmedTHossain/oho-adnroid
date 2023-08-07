package com.oho.oho.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.databinding.ActivityLoginBinding;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.LoginViewModel;
import com.oho.oho.views.settings.PrivacyPolicyActivity;
import com.oho.oho.views.settings.TermsOfUseActivity;

import static com.oho.oho.utils.Constants.RC_SIGN_IN;
import static com.oho.oho.utils.HelperClass.logErrorMessage;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private GoogleSignInClient googleSignInClient;
    private static final String TAG = "LoginActivity";

    ActivityLoginBinding binding;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OHO);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        initSignInButton();
        initAuthViewModel();
        initGoogleSignInClient();

        binding.termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, TermsOfUseActivity.class));
            }
        });
        binding.privacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PrivacyPolicyActivity.class));
            }
        });
    }

    private void initSignInButton() {
        binding.buttonSignin.setOnClickListener(v -> signIn());
    }

    private void initAuthViewModel() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private void signIn() {
        binding.loginProgress.setVisibility(View.VISIBLE);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"inside onActivityResult of LoginActivity = "+"YES");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    getGoogleAuthCredential(googleSignInAccount);
                }
            } catch (ApiException e) {
                logErrorMessage(e.getMessage());
            }
        }
    }

    private void getGoogleAuthCredential(GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithGoogleAuthCredential(googleAuthCredential);
    }

    private void signInWithGoogleAuthCredential(AuthCredential googleAuthCredential) {
        loginViewModel.signInWithGoogle(googleAuthCredential);
        loginViewModel.authenticatedUserLiveData.observe(this, authenticatedUser -> {
            loginViewModel.checkIfUserExists(authenticatedUser.getEmail());
            loginViewModel.userProfileData.observe(this, userProfile -> {
                if (userProfile == null) {
                    Intent intent = new Intent(this, OnboardingActivity.class);
                    intent.putExtra("name",authenticatedUser.getDisplayName());
                    intent.putExtra("email",authenticatedUser.getEmail());
                    intent.putExtra("phone",authenticatedUser.getPhoneNumber());
                    Log.d("LoginActivity","phone from login activity = "+authenticatedUser.getPhoneNumber());
                    startActivity(intent);
                    finish();
                }else {
                    saveCustomObject(this,userProfile);
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            });
        });
    }
    public static void saveCustomObject(Context context, Profile profile) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE).edit();
        editor.putString("PROFILE", profile.toJsonString());
        editor.apply();
    }
}