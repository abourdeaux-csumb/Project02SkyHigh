package com.example.project02skyhigh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02skyhigh.DB.UserRepository;
import com.example.project02skyhigh.databinding.ActivityAdminManageUsersBinding;

import java.util.List;

public class AdminManageUsersActivity extends AppCompatActivity {
    /**
     * Title: AdminManageUsersActivity.java
     * Abstract: Activity used for managing users
     * Author: Aaron Bourdeaux
     * Date: 2023/04/16
     */
    ActivityAdminManageUsersBinding mAdminManageUsersBinding;
    Button mDeleteUserButton;
    Button mGoBackButton;
    ListView mAdminManageUsersUserList;
    User mSelectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminManageUsersBinding = ActivityAdminManageUsersBinding.inflate(getLayoutInflater());
        View view = mAdminManageUsersBinding.getRoot();
        setContentView(view);
        wireupDisplay();
    }

    /**
     * Enable control of various elements in the layout
     */
    private void wireupDisplay() {
        mAdminManageUsersUserList = mAdminManageUsersBinding.manageUsersUserList;
        mDeleteUserButton = mAdminManageUsersBinding.adminDeleteUserButton;
        mGoBackButton = mAdminManageUsersBinding.adminManageUsersGoBackButton;
        populateUserList();
        setOnClickListeners();
    }

    /**
     * Populates the user list with selectable User elements
     */
    private void populateUserList() {
        List<User> users = UserRepository.getUsers();
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                users);
        mAdminManageUsersUserList.setAdapter(arrayAdapter);
    }

    /**
     * Dictates logic regarding click events
     */
    private void setOnClickListeners() {
        /*
        Attempts to delete the selected user
         */
        mDeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                /*
                Display error if a user hasn't been selected
                 */
                if (mDeleteUserButton == null) {
                    message = "Please select a user to delete.";
                }
                else {
                    SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
                    int userId = sharedPref.getInt("Login", -1);
                    /*
                    Display error if user selected is the same as the logged in user
                     */
                    if (mSelectedUser.getUserId() == userId) {
                        message = "Cannot delete currently logged in user.";
                    }
                    else {
                        UserRepository.deleteUser(mSelectedUser);
                        message = "Deleted user " + mSelectedUser.getUsername();
                        populateUserList();
                    }
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        /*
        Selects a user upon clicking on the user in the list
         */
        mAdminManageUsersUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedUser = (User)mAdminManageUsersUserList.getItemAtPosition(i);

            }
        });

        /*
        Go back to previous activity
         */
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminMenuActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /*
    Intent factory to switch between activities
    */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminManageUsersActivity.class);
        return intent;
    }
}