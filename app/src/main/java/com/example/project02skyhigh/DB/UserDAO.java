package com.example.project02skyhigh.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project02skyhigh.User;

import java.util.List;

/**
 *author: Aaron Bourdeaux
 *date: 2023/04/07
 *description: UserDAO.java is used to perform
 *             database operations on the User room table
 */

@Dao
public interface UserDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userID" )
    User getUserWithId(int userID);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :username" )
    User getUserWithUsername(String username);
}
