package com.example.project02skyhigh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02skyhigh.DB.FlightRepository;
import com.example.project02skyhigh.databinding.ActivityAdminAddFlightBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminAddFlightActivity extends AppCompatActivity {

    ActivityAdminAddFlightBinding mAdminAddFlightBinding;

    EditText mAddFlightOrigin;
    EditText mAddFlightDestination;
    EditText mAddFlightDepartureDate;
    EditText mAddFlightDepartureTime;
    EditText mAddFlightArrivalDate;
    EditText mAddFlightArrivalTime;
    Button mAddFlightButton;
    Button mGoBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminAddFlightBinding = ActivityAdminAddFlightBinding.inflate(getLayoutInflater());
        View view = mAdminAddFlightBinding.getRoot();
        setContentView(view);
        wireupDisplay();
    }

    private void wireupDisplay() {
        mAddFlightOrigin = mAdminAddFlightBinding.addFlightOrigin;
        mAddFlightDestination = mAdminAddFlightBinding.addFlightDestination;
        mAddFlightDepartureDate = mAdminAddFlightBinding.addFlightDepartureDate;
        mAddFlightDepartureTime = mAdminAddFlightBinding.addFlightDepartureTime;
        mAddFlightArrivalDate = mAdminAddFlightBinding.addFlightArrivalDate;
        mAddFlightArrivalTime = mAdminAddFlightBinding.addFlightArrivalTime;
        mAddFlightButton = mAdminAddFlightBinding.adminAddFlightButton;
        mGoBackButton = mAdminAddFlightBinding.adminAddFlightGoBackButton;
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        /*
        Book a flight
         */
        mAddFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flight flightToAdd = new Flight();
                flightToAdd.setOrigin(mAddFlightOrigin.getText().toString());
                flightToAdd.setDestination(mAddFlightDestination.getText().toString());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String departureDateString = mAddFlightDepartureDate.getText().toString() + " " + mAddFlightDepartureTime.getText().toString();
                String arrivalDateString = mAddFlightArrivalDate.getText().toString() + " " + mAddFlightArrivalTime.getText().toString();
                try {
                    flightToAdd.setDeparture(formatter.parse(departureDateString));
                    flightToAdd.setArrival(formatter.parse(arrivalDateString));
                }
                catch (ParseException ex) {
                    Toast.makeText(getApplicationContext(), "Cannot parse either supplied departure or arrival date", Toast.LENGTH_LONG).show();
                    return;
                }
                FlightRepository.addFlight(flightToAdd);
                Toast.makeText(getApplicationContext(), "Added flight from " + flightToAdd.getOrigin() + " to " + flightToAdd.getDestination(), Toast.LENGTH_LONG).show();
                Intent intent = AdminManageFlightsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminManageFlightsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /*
    Intent factory to switch between activities
    */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminAddFlightActivity.class);
        return intent;
    }
}