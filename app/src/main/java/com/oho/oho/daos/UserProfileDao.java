package com.oho.oho.daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.oho.oho.entities.UserProfile;
@Dao
public interface UserProfileDao {
    @Update
    void updateUserProfile(UserProfile userProfile);

    @Query("SELECT * FROM userprofile WHERE user_profile_id = :userId")
    UserProfile getUserProfile(int userId);
}
