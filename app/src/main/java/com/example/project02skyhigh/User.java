package com.example.project02skyhigh;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02skyhigh.DB.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {
    /**
     *author: Aaron Bourdeaux
     *date: 2023/04/07
     *description: User.java is a model used to represent User objects
     *             that are stored in the User database table
     */
    @PrimaryKey(autoGenerate = true)
    private int mUserId;
    private String mUsername;
    private String mPassword;
    private Boolean mIsAdmin;

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public Boolean getIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(Boolean mIsAdmin) {
        this.mIsAdmin = mIsAdmin;
    }


    @Override
    public String toString() {
        return "Username: " + mUsername + "\n" +
                "User Type: " + (mIsAdmin ? "Admin" : "Standard");
    }
}
