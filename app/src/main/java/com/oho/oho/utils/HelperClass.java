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
}
