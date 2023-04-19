package com.example.project02skyhigh.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project02skyhigh.Booking;
import com.example.project02skyhigh.Flight;

import java.util.List;

/**
 *author: Aaron Bourdeaux
 *date: 2023/04/14
 *description: BookingDAO.java is used to perform
 *             database operations on the Bookings room table
 */

@Dao
public interface BookingsDAO {
    @Insert
    void insert(Booking... bookings);

    @Update
    void update(Booking... bookings);

    @Delete
    void delete(Booking booking);

    @Query("SELECT * FROM " + AppDatabase.BOOKINGS_TABLE)
    List<Booking> getBookings();

    /**
     * Returns the flights info of all bookings
     * associated with a particular user
     */
    @Query("SELECT ft.mFLightId, ft.mOrigin, ft.mDestination, ft.mArrival, ft.mDeparture FROM " + AppDatabase.BOOKINGS_TABLE
            + " bt INNER JOIN " + AppDatabase.FLIGHT_TABLE + " ft"
            + " ON bt.mFlightId = ft.mFLightId"
            + " WHERE mUserId = :userId" )
    List<Flight> getFlightBookingsByUser(int userId);

}
