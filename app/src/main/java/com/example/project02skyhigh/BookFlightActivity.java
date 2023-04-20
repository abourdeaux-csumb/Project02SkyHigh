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
import com.example.project02skyhigh.databinding.ActivityBookFlightBinding;

import java.util.ArrayList;
import java.util.List;

public class BookFlightActivity extends AppCompatActivity {
    /**
     * Title: BookFlightActivity.java
     * Abstract: Activity used for booking a flight
     * Author: Aaron Bourdeaux
     * Date: 2023/04/15
     */
    ActivityBookFlightBinding mBookFlightBinding;
    Button mBookFlightButton;
    Button mGoBackButton;
    ListView mBookFlightFlightList;
    Flight mFlightToBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBookFlightBinding = ActivityBookFlightBinding.inflate(getLayoutInflater());
        View view = mBookFlightBinding.getRoot();
        setContentView(view);
        wireupDisplay();
    }

    /**
     * Enable control of various elements in the layout
     */
    private void wireupDisplay() {
        mBookFlightFlightList = mBookFlightBinding.bookFlightFlightList;
        mBookFlightButton = mBookFlightBinding.bookFlightButton;
        mGoBackButton = mBookFlightBinding.bookFlightGoBackButton;
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
        mBookFlightFlightList.setAdapter(arrayAdapter);
    }

    /**
     * Dictates logic regarding click events
     */
    private void setOnClickListeners() {
        /*
        Book a flight
         */
        mBookFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                /*
                Display error if a flight is not selected
                 */
                if (mFlightToBook == null) {
                    message = "Please select a flight to book.";
                }
                /*
                Attempt to book a flight
                 */
                else {
                    SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
                    int userId = sharedPref.getInt("Login", -1);
                    int bookFlightResult = FlightRepository.bookFlight(mFlightToBook.getFLightId(), userId);
                    /*
                    Do not book the flight if the user has already booked it
                     */
                    if (bookFlightResult == -1) {
                        message = "Flight already booked.";
                    }
                    else {
                        message = "Booked flight from " + mFlightToBook.getOrigin() + " to " + mFlightToBook.getDestination() + ".";
                    }
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        /*
        Selects a flight upon clicking on the flight in the list
         */
        mBookFlightFlightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mFlightToBook = (Flight)mBookFlightFlightList.getItemAtPosition(i);

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
    /*
    Intent factory to switch between activities
    */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, BookFlightActivity.class);
        return intent;
    }
}