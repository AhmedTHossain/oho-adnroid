package com.oho.oho.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.oho.oho.db.tables.UserPrompt;

import java.util.List;
@Dao
public interface UserPromptDao {
    @Insert
    void insertPrompt(UserPrompt userPrompt);

//    @Update
//    void updatePrompt(UserPrompt userPrompt, int orderNo);
//
//    @Query("Select * from userprompt")
//    List<UserPrompt> getAllPromptAnswers();
}
