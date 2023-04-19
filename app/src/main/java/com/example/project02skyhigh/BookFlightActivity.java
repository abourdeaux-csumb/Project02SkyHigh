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
    private void wireupDisplay() {
        mBookFlightFlightList = mBookFlightBinding.bookFlightFlightList;
        mBookFlightButton = mBookFlightBinding.bookFlightButton;
        mGoBackButton = mBookFlightBinding.bookFlightGoBackButton;
        populateFlightList();
        setOnClickListeners();
    }

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

    private void setOnClickListeners() {
        /*
        Book a flight
         */
        mBookFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                if (mFlightToBook == null) {
                    message = "Please select a flight to book.";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences sharedPref = getSharedPreferences("Logins", Context.MODE_PRIVATE);
                    int userId = sharedPref.getInt("Login", -1);
                    int bookFlightResult = FlightRepository.bookFlight(mFlightToBook.getFLightId(), userId);
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

        mBookFlightFlightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mFlightToBook = (Flight)mBookFlightFlightList.getItemAtPosition(i);

            }
        });

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