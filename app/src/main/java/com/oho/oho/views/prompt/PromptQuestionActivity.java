package com.oho.oho.views.prompt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.oho.oho.R;

public class PromptQuestionActivity extends AppCompatActivity {

    private View fabButton;
    private int promptNumber, promptPhotoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_question);

        promptNumber  = getIntent().getIntExtra("promptNumber",0);
        promptPhotoId = getIntent().getIntExtra("promptPhotoId",promptPhotoId);

        Log.d("PromptQuestionActivity","promptNumber = "+promptNumber+ " & promptPhotoId = "+promptPhotoId);

        fabButton = findViewById(R.id.button_fab_prompt);

        Fragment fragment;
        fragment = new PromptListFragment();
        loadFragment(fragment);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("prompt_selected", MODE_PRIVATE);
                int prompt_id = sharedPref.getInt("id", 0);
                if (prompt_id != 0) {
                    Fragment fragment;
                    fragment = new PromptAnswerFragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("promptPhotoId", promptPhotoId);
                    bundle.putInt("promptNumber", promptNumber);

                    fragment.setArguments(bundle);

                    loadFragment(fragment);
                    fabButton.setVisibility(View.GONE);
                } else
                    Toast.makeText(getApplicationContext(), "Please select a prompt first!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}