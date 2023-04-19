package com.example.project02skyhigh.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project02skyhigh.Flight;
import java.util.List;

/**
 *author: Aaron Bourdeaux
 *date: 2023/04/14
 *description: FlightDAO.java is used to perform
 *             database operations on the Flight room table
 */

@Dao
public interface FlightDAO {
    @Insert
    void insert(Flight... flights);

    @Update
    void update(Flight... flights);

    @Delete
    void delete(Flight flight);

    @Query("SELECT * FROM " + AppDatabase.FLIGHT_TABLE)
    List<Flight> getFlights();

    @Query("SELECT * FROM " + AppDatabase.FLIGHT_TABLE + " WHERE mFlightId = :flightId" )
    Flight getFlightWithId(int flightId);

}
