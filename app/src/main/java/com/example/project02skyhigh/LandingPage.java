package com.example.project02skyhigh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project02skyhigh.DB.AppDatabase;
import com.example.project02skyhigh.DB.FlightRepository;
import com.example.project02skyhigh.DB.UserDAO;
import com.example.project02skyhigh.databinding.ActivityLandingpageBinding;

public class LandingPage extends AppCompatActivity {

    TextView mLandingHeader;
    Button mLandingFlightsButton;
    Button mLandingBookedFlightsButton;
    Button mLandingAdminButton;
    Button mLandingSignOutButton;
    ActivityLandingpageBinding mLandingPageBinding;
    UserDAO mUserDAO;

    User mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLandingPageBinding = ActivityLandingpageBinding.inflate(getLayoutInflater());
        View view = mLandingPageBinding.getRoot();
        setContentView(view);
        wireUpDisplay();
        FlightRepository.initialize(this);
    }

    private void wireUpDisplay() {
        mLandingHeader = mLandingPageBinding.landingSkyHighHeader;
        mLandingFlightsButton = mLandingPageBinding.landingFlightsButton;
        mLandingBookedFlightsButton = mLandingPageBinding.landingBookedFlightsButton;
        mLandingAdminButton = mLandingPageBinding.landingAdminButton;
        mLandingSignOutButton = mLandingPageBinding.landingSignOutButton;
        evaluateUserPermissions();
        mLandingHeader.setText("Welcome, " + mUser.getUsername());
        setButtonOnClickListeners();
    }


    private void evaluateUserPermissions() {
        SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
        int userId = sharedPref.getInt("Login", -1);
        if (userId == -1) {
            Intent intent = MainActivity.getIntent(getApplicationContext());
            startActivity(intent);
        }
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        mUser = mUserDAO.getUserWithId(userId);
        if(mUser.getIsAdmin()) {
            mLandingAdminButton.setVisibility(View.VISIBLE);
        }
    }

    private void setButtonOnClickListeners() {
        mLandingSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =  sharedPref.edit();
                editor.remove("Login");
                editor.commit();
                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mLandingFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = BookFlightActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mLandingBookedFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ManageBookedFlightsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mLandingAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminMenuActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LandingPage.class);
        return intent;
    }
}
