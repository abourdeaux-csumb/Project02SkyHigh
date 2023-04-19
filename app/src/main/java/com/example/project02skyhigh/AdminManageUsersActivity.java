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
    private void wireupDisplay() {
        mAdminManageUsersUserList = mAdminManageUsersBinding.manageUsersUserList;
        mDeleteUserButton = mAdminManageUsersBinding.adminDeleteUserButton;
        mGoBackButton = mAdminManageUsersBinding.adminManageUsersGoBackButton;
        populateUserList();
        setOnClickListeners();
    }

    private void populateUserList() {
        List<User> users = UserRepository.getUsers();
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                users);
        mAdminManageUsersUserList.setAdapter(arrayAdapter);
    }

    private void setOnClickListeners() {
        /*
        Book a flight
         */
        mDeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                if (mDeleteUserButton == null) {
                    message = "Please select a user to delete.";
                }
                else {
                    SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
                    int userId = sharedPref.getInt("Login", -1);
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

        mAdminManageUsersUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedUser = (User)mAdminManageUsersUserList.getItemAtPosition(i);

            }
        });

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