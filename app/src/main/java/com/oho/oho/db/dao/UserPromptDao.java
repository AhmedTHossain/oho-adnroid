package com.oho.oho.db.dao;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.oho.oho.models.PromptAnswer;

import java.util.List;

public interface UserPromptDao {
    @Insert
    PromptAnswer insertPrompt(PromptAnswer promptAnswer);

    @Update
    PromptAnswer updatePrompt(PromptAnswer promptAnswer, int orderNo);

    @Query("Select * from UserPrompt")
    List<PromptAnswer> getAllPromptAnswers();
}
