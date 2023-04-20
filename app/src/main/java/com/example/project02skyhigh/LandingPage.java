package com.example.project02skyhigh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project02skyhigh.DB.AppDatabase;
import com.example.project02skyhigh.DB.FlightRepository;
import com.example.project02skyhigh.DB.UserDAO;
import com.example.project02skyhigh.databinding.ActivityLandingpageBinding;

import java.util.Date;
import java.util.List;

public class LandingPage extends AppCompatActivity {
    /**
     * Title: LandingPage.java
     * Abstract: Activity used as a main menu after logging in
     * Author: Aaron Bourdeaux
     * Date: 2023/04/09
     */
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
        checkForUpcomingDeparture();
    }

    /**
     * Notify user if they have a flight booked that is
     * departing in the next 24 hours
     */
    private void checkForUpcomingDeparture() {
        List<Flight> flights = FlightRepository.getFlightsByUserId(mUser.getUserId());
        long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000L;
        long timeDiff;
        long todayInMs = (new Date()).getTime();
        for (Flight flight : flights) {
            timeDiff = flight.getDeparture().getTime() - todayInMs;
            if (timeDiff < MILLISECONDS_PER_DAY && timeDiff > 0) {
                Toast.makeText(getApplicationContext(), "You have a flight departing in less than 24 hours.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Enable control of various elements in the layout
     */
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

    /**
     * Determines visibility of admin button based on
     * the current user
     */
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

    /**
     * Dictates logic regarding click events
     */
    private void setButtonOnClickListeners() {
        /**
         * Sign out user and redirect to MainActivity
         */
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

        /**
         * Redirect to BookFlightActivity
         */
        mLandingFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = BookFlightActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        /**
         * Redirect to ManageBookedFlightsActivity
         */
        mLandingBookedFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ManageBookedFlightsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        /**
         * Redirect to AdminMenuActivity
         */
        mLandingAdminButton.setOnClickListener(new View.OnClickListener() {
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
        Intent intent = new Intent(context, LandingPage.class);
        return intent;
    }
}
