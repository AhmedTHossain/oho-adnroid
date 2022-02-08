package com.oho.oho.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.oho.oho.viewmodels.LoginViewModel;
import static com.oho.oho.utils.Constants.RC_SIGN_IN;
import static com.oho.oho.utils.HelperClass.logErrorMessage;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private GoogleSignInClient googleSignInClient;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initSignInButton();
        initAuthViewModel();
        initGoogleSignInClient();
    }

    private void initSignInButton() {
        CardView googleSignInButton = findViewById(R.id.button_signin);
        googleSignInButton.setOnClickListener(v -> signIn());
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
        ProgressBar progressBar = findViewById(R.id.login_progress);
        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    startActivity(intent);
                    finish();
                }else {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            });
        });
    }

}