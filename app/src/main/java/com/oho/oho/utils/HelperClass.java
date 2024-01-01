package com.oho.oho.utils;

import static com.oho.oho.utils.Constants.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.oho.oho.models.Profile;

public class HelperClass {
    public static void logErrorMessage(String errorMessage) {
        Log.d(TAG, errorMessage);
    }

    public void saveProfile(Context context, Profile profile) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE).edit();
        if (profile == null) {
            editor.putString("PROFILE", null);
        } else {
            editor.putString("PROFILE", profile.toJsonString());
        }
        editor.apply();
    }

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

    public void setJWTToken(Context context, String JWTToken){
        SharedPreferences.Editor editor = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE).edit();
        editor.putString("JWT", JWTToken);
        editor.apply();
    }

    public String getJWTToken(Context context){
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
}
