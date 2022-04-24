package com.oho.oho.db.dao;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.oho.oho.db.tables.UserPrompt;

import java.util.List;

public interface UserPromptDao {
    @Insert
    UserPrompt insertPrompt(UserPrompt userPrompt);

    @Update
    UserPrompt updatePrompt(UserPrompt userPrompt, int orderNo);

    @Query("Select * from UserPrompt")
    List<UserPrompt> getAllPromptAnswers();
}
