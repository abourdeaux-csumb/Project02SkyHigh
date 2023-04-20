package com.example.project02skyhigh;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02skyhigh.DB.AppDatabase;

import java.util.Date;


@Entity(tableName = AppDatabase.FLIGHT_TABLE)
public class Flight {
    /**
     *author: Aaron Bourdeaux
     *date: 2023/04/07
     *description: Flight.java is a model used to represent Flight objects
     *             that are stored in the Flight database table
     */
    @PrimaryKey(autoGenerate = true)
    private int mFLightId;
    private String mOrigin;
    private String mDestination;
    private Date mArrival;
    private Date mDeparture;

    public int getFLightId() {
        return mFLightId;
    }

    public void setFLightId(int mFLightId) {
        this.mFLightId = mFLightId;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String mOrigin) {
        this.mOrigin = mOrigin;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String mDestination) {
        this.mDestination = mDestination;
    }

    public Date getArrival() {
        return mArrival;
    }

    public void setArrival(Date mArrival) {
        this.mArrival = mArrival;
    }

    public Date getDeparture() {
        return mDeparture;
    }

    public void setDeparture(Date mDeparture) {
        this.mDeparture = mDeparture;
    }

    @Override
    public String toString() {
        return "Origin: " + mOrigin + "\n" +
                "Destination: " + mDestination + "\n" +
                "Departure: " + mDeparture + "\n" +
                "Arrival: " + mArrival + "\n";
    }
}
