package com.example.project02skyhigh.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.project02skyhigh.Booking;
import com.example.project02skyhigh.DB.typeconverter.DateTypeConverter;
import com.example.project02skyhigh.Flight;
import com.example.project02skyhigh.User;

@Database(entities = {User.class, Flight.class, Booking.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Title: AppDatabase.java
     * Abstract: Room database wrapper class for SQLlite db
     * Author: Aaron Bourdeaux
     * Date: 2023/04/03
     */
    public static final String dbName = "db-SkyHigh";
    public static final String USER_TABLE = "user";

    public static final String FLIGHT_TABLE = "flight";

    public static final String BOOKINGS_TABLE = "bookings";

    public abstract UserDAO getUserDAO();
    public abstract FlightDAO getFlightDAO();
    public abstract BookingsDAO getBookingsDAO();
}
