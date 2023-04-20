package com.example.project02skyhigh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02skyhigh.databinding.ActivityAdminMenuBinding;

public class AdminMenuActivity extends AppCompatActivity {
    /**
     * Title: AdminMenuActivityActivity.java
     * Abstract: Activity used as a menu for redirecting to admin-specific activities
     * Author: Aaron Bourdeaux
     * Date: 2023/04/12
     */
    Button mAdminMenuManageFlightsButton;
    Button mAdminMenuManageUsersButton;
    Button mAdminMenuManageGoBackButton;
    ActivityAdminMenuBinding mAdminMenuBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminMenuBinding = ActivityAdminMenuBinding.inflate(getLayoutInflater());
        View view = mAdminMenuBinding.getRoot();
        setContentView(view);
        wireUpDisplay();
    }

    /**
     * Enable control of various elements in the layout
     */
    private void wireUpDisplay() {
        mAdminMenuManageFlightsButton = mAdminMenuBinding.adminMenuManageFlightsButton;
        mAdminMenuManageUsersButton = mAdminMenuBinding.adminMenuManageUsersButton;
        mAdminMenuManageGoBackButton = mAdminMenuBinding.adminMenuGoBackButton;
        setButtonOnClickListeners();
    }

    /**
     * Dictates logic regarding click events
     */
    private void setButtonOnClickListeners() {
        /*
        Redirect to AdminManageFlightsActivity
         */
        mAdminMenuManageFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminManageFlightsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        /*
        Redirect to AdminManageUsersActivity
         */
        mAdminMenuManageUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminManageUsersActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        /*
        Go back to previous activity
         */
        mAdminMenuManageGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /*
    Intent factory to switch between activities
    */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminMenuActivity.class);
        return intent;
    }
}