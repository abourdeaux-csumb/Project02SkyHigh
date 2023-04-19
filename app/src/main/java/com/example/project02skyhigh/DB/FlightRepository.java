package com.example.project02skyhigh.DB;

import android.content.Context;

import androidx.room.Room;

import com.example.project02skyhigh.Booking;
import com.example.project02skyhigh.Flight;

import java.util.Date;
import java.util.List;

public class FlightRepository {

    private static FlightDAO mFlightDAO;
    private static BookingsDAO mBookingsDAO;
    private int userId;

    public static void initialize(Context context) {
        mFlightDAO = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getFlightDAO();
        mBookingsDAO = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getBookingsDAO();
        populateFlights();
    }

    public static List<Flight> getFlights() {
        return mFlightDAO.getFlights();
    }

    public static List<Flight> getFlightsByUserId(int userId) {
        return mBookingsDAO.getFlightBookingsByUser(userId);
    }

    private static void populateFlights() {
        List<Flight> flights = mFlightDAO.getFlights();
        if (flights.isEmpty()) {
            Flight flightOne = new Flight();
            flightOne.setOrigin("LAX");
            flightOne.setDestination("ATL");
            flightOne.setDeparture(new Date(2023, 5, 12, 5, 45));
            flightOne.setArrival(new Date(2023, 5, 12, 13, 40));

            Flight flightTwo = new Flight();
            flightTwo.setOrigin("DFW");
            flightTwo.setDestination("SNA");
            flightTwo.setDeparture(new Date(2023, 5, 1, 19, 3));
            flightTwo.setArrival(new Date(2023, 5, 1, 20, 20));

            Flight flightThree = new Flight();
            flightThree.setOrigin("ORD");
            flightThree.setDestination("MIA");
            flightThree.setDeparture(new Date(2023, 5, 4, 14, 25));
            flightThree.setArrival(new Date(2023, 5, 4, 18, 39));

            mFlightDAO.insert(flightOne, flightTwo, flightThree);

        }
    }

    public static int bookFlight(int flightId, int userId) {
        List<Flight> bookings = mBookingsDAO.getFlightBookingsByUser(userId);
        for (Flight flight : bookings) {
            if (flight.getFLightId() == flightId) {
                return -1;
            }
        }
        Booking booking = new Booking();
        booking.setFlightId(flightId);
        booking.setUserId(userId);
        booking.setCreateTime(new Date());
        mBookingsDAO.insert(booking);
        return 1;
    }

    public static int cancelFlight(int flightId, int userId) {
        List<Booking> bookings = mBookingsDAO.getBookings();
        for (Booking booking : bookings) {
            if (booking.getFlightId() == flightId && booking.getUserId() == userId) {
                mBookingsDAO.delete(booking);
                return 1;
            }
        }
        return -1;
    }

    public static void deleteFlight(int flightId) {
        List<Booking> bookings = mBookingsDAO.getBookings();
        for (Booking booking : bookings) {
            if (booking.getFlightId() == flightId) {
                mBookingsDAO.delete(booking);
            }
        }
        Flight flightToDelete = mFlightDAO.getFlightWithId(flightId);
        mFlightDAO.delete(flightToDelete);
    }

    public static void addFlight(Flight flight) {
        mFlightDAO.insert(flight);
    }

}
