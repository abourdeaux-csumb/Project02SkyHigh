package com.example.project02skyhigh.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.project02skyhigh.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String dbName = "db-SkyHigh";
    public static final String USER_TABLE = "user";
    public abstract UserDAO getUserDAO();
}
