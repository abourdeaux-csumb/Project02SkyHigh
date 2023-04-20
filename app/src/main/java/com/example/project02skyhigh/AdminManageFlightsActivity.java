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
import com.example.project02skyhigh.databinding.ActivityAdminManageFlightsBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminManageFlightsActivity extends AppCompatActivity {
    /**
     * Title: AdminManageFlightActivity.java
     * Abstract: Activity used for managing flights
     * Author: Aaron Bourdeaux
     * Date: 2023/04/16
     */

    ActivityAdminManageFlightsBinding mAdminManageFlightsBinding;
    Button mAddFlightButton;
    Button mDeleteFlightButton;
    Button mGoBackButton;
    ListView mAdminManageFlightList;
    Flight mSelectedFlight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminManageFlightsBinding = ActivityAdminManageFlightsBinding.inflate(getLayoutInflater());
        View view = mAdminManageFlightsBinding.getRoot();
        setContentView(view);
        wireupDisplay();
    }

    /**
     * Enable control of various elements in the layout
     */
    private void wireupDisplay() {
        mAdminManageFlightList = mAdminManageFlightsBinding.manageFlightsFlightList;
        mAddFlightButton = mAdminManageFlightsBinding.adminAddFlightButton;
        mDeleteFlightButton = mAdminManageFlightsBinding.adminDeleteFlightButton;
        mGoBackButton = mAdminManageFlightsBinding.adminManageFlightsGoBackButton;
        populateFlightList();
        setOnClickListeners();
    }

    /**
     * Populates the flight list with selectable Flight elements
     */
    private void populateFlightList() {
        List<String> flightInfo = new ArrayList<String>();
        List<Flight> flights = FlightRepository.getFlights();
        for (Flight flight : flights) {
            flightInfo.add(flight.toString());
        }
        ArrayAdapter<Flight> arrayAdapter = new ArrayAdapter<Flight>(
                this,
                android.R.layout.simple_list_item_1,
                flights);
        mAdminManageFlightList.setAdapter(arrayAdapter);
    }

    /**
     * Dictates logic regarding click events
     */
    private void setOnClickListeners() {
        /*
        Redirect to Activity for adding a flight
         */
        mAddFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminAddFlightActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        /*
        Attempts to delete a selected flight
         */
        mDeleteFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                /*
                Display error if no flight has been selected
                 */
                if (mSelectedFlight == null) {
                    message = "Please select a flight to delete.";
                }
                /*
                Delete flight and update flight list upon deletion
                 */
                else {
                    SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
                    int userId = sharedPref.getInt("Login", -1);
                    FlightRepository.deleteFlight(mSelectedFlight.getFLightId());
                    message = "Deleted flight from " + mSelectedFlight.getOrigin() + " to " + mSelectedFlight.getDestination() + ".";
                    populateFlightList();
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        /*
        Selects a flight upon clicking on the flight in the list
         */
        mAdminManageFlightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedFlight = (Flight)mAdminManageFlightList.getItemAtPosition(i);
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
        Intent intent = new Intent(context, AdminManageFlightsActivity.class);
        return intent;
    }
}