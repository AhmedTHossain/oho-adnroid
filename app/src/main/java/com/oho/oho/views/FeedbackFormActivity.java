package com.oho.oho.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.oho.oho.R;
import com.oho.oho.databinding.ActivityFeedbackFormBinding;
import com.oho.oho.models.FeebackPostRequest;
import com.oho.oho.models.notifications.FeedbackNotificationPayload;

public class FeedbackFormActivity extends AppCompatActivity {
    private ActivityFeedbackFormBinding binding;
    private int matchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackFormBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_OHO);
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("notificationPayload")) {
            FeedbackNotificationPayload notificationPayload = (FeedbackNotificationPayload) getIntent().getSerializableExtra("notificationPayload");

            String quesForDate = "How did your date go with " + notificationPayload.getSenderName() + "?";
            binding.quesForDate.setText(quesForDate);

            String quesForPlace = "Your thoughts on " + notificationPayload.getRestaurantName() + "?";
            binding.quesForPlace.setText(quesForPlace);

            matchId = notificationPayload.getMatchId();

            binding.submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FeebackPostRequest request = new FeebackPostRequest();
                    request.setMatchId(String.valueOf(matchId));
                    request.setDateRating(String.valueOf(binding.ratingBarDate.getRating()));
                    request.setRestaurantRating(String.valueOf(binding.ratingBarPlace.getRating()));
                    request.setComment(binding.editTextComment.getText().toString());
                    //TODO: Call the function to post/submit user's feedback
                }
            });
        }
    }
}