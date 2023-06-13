package com.oho.oho.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.oho.oho.converters.Converters;
import com.oho.oho.daos.UserProfileDao;
import com.oho.oho.entities.UserProfile;

@Database(entities = UserProfile.class, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class LocalDatabase extends RoomDatabase {
    private static final String DB_NAME = "user_profile_db";
    private static LocalDatabase instance;

    public static synchronized LocalDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserProfileDao userProfileDao();
}
