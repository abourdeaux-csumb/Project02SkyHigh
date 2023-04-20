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

import com.example.project02skyhigh.DB.FlightRepository;
import com.example.project02skyhigh.databinding.ActivityManageBookedFlightsBinding;

import java.util.ArrayList;
import java.util.List;

public class ManageBookedFlightsActivity extends AppCompatActivity {
    /**
     * Title: ManageBookedFlightsActivity.java
     * Abstract: Activity used for managing bookings
     * Author: Aaron Bourdeaux
     * Date: 2023/04/15
     */
    ActivityManageBookedFlightsBinding mManageBookedFlightsBinding;
    Button mCancelBookedFlightButton;
    Button mGoBackButton;
    Flight mFlightToCancel;
    ListView mManageBookedFlightsFlightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManageBookedFlightsBinding = ActivityManageBookedFlightsBinding.inflate(getLayoutInflater());
        View view = mManageBookedFlightsBinding.getRoot();
        setContentView(view);
        wireupDisplay();
    }

    /**
     * Enable control of various elements in the layout
     */
    private void wireupDisplay() {
        mManageBookedFlightsFlightList = mManageBookedFlightsBinding.manageBookedFlightsFlightList;
        mCancelBookedFlightButton = mManageBookedFlightsBinding.cancelBookedFlightButton;
        mGoBackButton = mManageBookedFlightsBinding.bookedFlightsGoBackButton;
        populateFlightList();
        setOnClickListeners();
    }

    /**
     * Dictates logic regarding click events
     */
    private void setOnClickListeners() {
        /*
        Cancel a booked flight
         */
        mCancelBookedFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                /*
                Display error if no flight is selected
                 */
                if (mFlightToCancel == null) {
                    message = "Please select a flight to cancel.";
                }
                else {
                    SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
                    int userId = sharedPref.getInt("Login", -1);
                    int cancelFlightResult = FlightRepository.cancelFlight(mFlightToCancel.getFLightId(), userId);
                    if (cancelFlightResult == -1) {
                        message = "Cannot cancel flight.";
                    }
                    else {
                        message = "Cancelled flight from " + mFlightToCancel.getOrigin() + " to " + mFlightToCancel.getDestination() + ".";
                    }
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                populateFlightList();
            }
        });

        /*
        Selects a flight upon clicking on the flight in the list
         */
        mManageBookedFlightsFlightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mFlightToCancel = (Flight)mManageBookedFlightsFlightList.getItemAtPosition(i);

            }
        });

        /*
        Go back to previous activity
         */
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /**
     * Populates the booking list with selectable Flight/Booking elements
     */
    private void populateFlightList() {
        SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
        int userId = sharedPref.getInt("Login", -1);
        List<String> flightInfo = new ArrayList<String>();
        List<Flight> flights = FlightRepository.getFlightsByUserId(userId);
        for (Flight flight : flights) {
            flightInfo.add(flight.toString());
        }
        ArrayAdapter<Flight> arrayAdapter = new ArrayAdapter<Flight>(
                this,
                android.R.layout.simple_list_item_1,
                flights);
        mManageBookedFlightsFlightList.setAdapter(arrayAdapter);
    }
    /*
    Intent factory to switch between activities
    */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ManageBookedFlightsActivity.class);
        return intent;
    }
}