package com.oho.oho.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.oho.oho.db.dao.UserPromptDao;
import com.oho.oho.db.tables.UserPrompt;

@Database(entities = {UserPrompt.class}, exportSchema = true, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserPromptDao getUserPromptDao();
}
