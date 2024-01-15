package com.oho.oho.utils;

import static com.oho.oho.utils.Constants.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.oho.oho.models.Profile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HelperClass {
    public static void logErrorMessage(String errorMessage) {
        Log.d(TAG, errorMessage);
    }

    //Store logged in user's profile locally
    public void saveProfile(Context context, Profile profile) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE).edit();
        if (profile == null) {
            editor.putString("PROFILE", null);
        } else {
            editor.putString("PROFILE", profile.toJsonString());
        }
        editor.apply();
    }

    //Retrieve logged in user's profile stored locally
    public Profile getProfile(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE);
        String jsonString = prefs.getString("PROFILE", null);
        if (jsonString != null) {
            return Profile.fromJsonString(jsonString);
        } else {
            // Return a default object or null if no object found in SharedPreferences
            return null;
        }
    }

    //Store logged in user's jwt token locally
    public void setJWTToken(Context context, String JWTToken) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE).edit();
        editor.putString("JWT", JWTToken);
        editor.apply();
    }

    //Retrieve logged in user's jwt token stored locally
    public String getJWTToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE);
        String jwtToken = prefs.getString("JWT", null);
        return jwtToken;
    }

    // converts height in cm to feet and inches for display
    public String convertToFeetAndInches(double heightInCentimeters) {
        // Convert height to inches
        double totalInch = heightInCentimeters / 2.54;

        // Calculate feet and remaining inches
        int feet = (int) (totalInch / 12);
        int remainingInches = (int) (totalInch % 12);

        // Build the formatted string
        String formattedHeight = feet + "' " + remainingInches + "''";

        return formattedHeight;
    }

    public double convertToCentimeters(int feet, int inches) {
        // Convert feet and inches to centimeters
        double totalInch = feet * 12 + inches;
        return totalInch * 2.54;
    }

    public double convertHeight(String heightString) {
        String stringHeight = heightString.split(" ft")[0];
        Log.d("HelperClass","string height = "+stringHeight);

        int feet = Integer.parseInt(stringHeight.split("\\.")[0]);
        int inch = Integer.parseInt(stringHeight.split("\\.")[1]);

        return convertToCentimeters(feet, inch);
    }

    //Find the dates of the upcoming dating phase
    public String getFridayToSundayDateRange() {
        Calendar calendar = Calendar.getInstance();
        // Set the calendar to the current date
        calendar.setTime(new Date());
        // Find the current day of the week
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Calculate the difference between the current day and Friday
        int daysUntilFriday = Calendar.FRIDAY - dayOfWeek;
        if (daysUntilFriday < 0) {
            daysUntilFriday += 7;
        }
        // Calculate the difference between the current day and Sunday
        int daysUntilSunday = Calendar.SUNDAY - dayOfWeek;
        if (daysUntilSunday < 0) {
            daysUntilSunday += 7;
        }
        // Add the differences to the current date to get Friday and Sunday
        calendar.add(Calendar.DAY_OF_WEEK, daysUntilFriday);
        Date friday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, daysUntilSunday - daysUntilFriday);
        Date sunday = calendar.getTime();
        // Format the dates in the specified format
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d", Locale.getDefault());
        String fridayString = sdf.format(friday);
        String sundayString = sdf.format(sunday);
        return fridayString + "th - " + sundayString + "th";
    }

    public void showSnackBar(View layout, String msg) {
        Snackbar snackbar = Snackbar.make(
                layout,
                msg,
                Snackbar.LENGTH_SHORT
        );

        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_FADE);

        // Set background color to white (#ffffff)
        snackbar.getView().setBackgroundColor(Color.parseColor("#000000"));

        // Set text color to black (#000000)
        TextView snackbarTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        snackbarTextView.setTextSize(16);
        snackbarTextView.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.show();
    }
}
