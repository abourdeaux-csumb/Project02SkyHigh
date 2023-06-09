package com.example.project02skyhigh.DB;

import android.content.Context;

import androidx.room.Room;

import com.example.project02skyhigh.User;

import java.util.List;

public class UserRepository {
    /**
     * Title: UserRepository.java
     * Abstract: Abstraction layer to communicate with FlightDAO and BookingsDAO
     * Author: Aaron Bourdeaux
     * Date: 2023/04/10
     */
    private static UserDAO mUserDAO;

    /**
     * Sets up the logic for communicating with UserRepository
     * @param context
     */
    public static void initialize(Context context) {
        mUserDAO = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
        populateUsers();
    }

    /**
     * Used on login to validate credentials, returns userId
     * @param username
     * @param password
     * @return
     */
    public static int validateCredentials(String username, String password) {
        List<User> mUsers = mUserDAO.getUsers();
        for (User user : mUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user.getUserId();
            }
        }
        return -1;
    }

    /**
     * Used to validate creation of new account
     * @param username
     * @param password
     * @param isAdmin
     * @return
     */
    public static int createAccount(String username, String password, boolean isAdmin) {
        List<User> mUsers = mUserDAO.getUsers();
        for (User user : mUsers) {
            if (user.getUsername().equals(username)) {
                return -1;
            }
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setIsAdmin(isAdmin);
        mUserDAO.insert(user);
        user = mUserDAO.getUserWithUsername(username);
        return user.getUserId();
    }


    /**
     * Seed User table with default values
     */
    private static void populateUsers() {
        List<User> users = mUserDAO.getUsers();
        if (mUserDAO.getUsers().isEmpty()) {
            User testUser = new User();
            testUser.setUsername("testuser1");
            testUser.setPassword("testuser1");
            testUser.setIsAdmin(false);

            User adminUser = new User();
            adminUser.setUsername("admin2");
            adminUser.setPassword("admin2");
            adminUser.setIsAdmin(true);

            mUserDAO.insert(testUser, adminUser);
        }
    }

    /**
     * Returns List of all Users
     * @return
     */
    public static List<User> getUsers() {
        return mUserDAO.getUsers();
    }

    /**
     * Delete a user
     * @param user
     */
    public static void deleteUser(User user) {
        mUserDAO.delete(user);
    }

}
