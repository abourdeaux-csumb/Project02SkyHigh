package com.example.project02skyhigh;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project02skyhigh.DB.AppDatabase;
import com.example.project02skyhigh.DB.BookingsDAO;
import com.example.project02skyhigh.DB.FlightDAO;
import com.example.project02skyhigh.DB.UserDAO;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTests {
    private UserDAO userDAO;
    private FlightDAO flightDAO;
    private BookingsDAO bookingsDAO;
    private AppDatabase db;

    /**
     * Setup db and DAO objects before running tests
     */
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDAO = db.getUserDAO();
        flightDAO = db.getFlightDAO();
        bookingsDAO = db.getBookingsDAO();
    }

    /**
     * Closes out the db after running tests
     * @throws IOException
     */
    @After
    public void closeDb() throws IOException {
        db.close();
    }

    /**
     * Tests insert for User table
     * @throws Exception
     */
    @Test
    public void insertUserAddsUserToUserTable() throws Exception {
        User user = new User();
        user.setUsername("Test01");
        user.setPassword("Test01");
        user.setIsAdmin(false);
        User returnedUser = userDAO.getUserWithUsername("Test01");
        Assert.assertTrue(returnedUser == null);
        userDAO.insert(user);
        returnedUser = userDAO.getUserWithUsername("Test01");
        Assert.assertTrue(returnedUser.getUsername().equals("Test01"));
    }

    /**
     * Tests update for User table
     * @throws Exception
     */
    @Test
    public void updateUserUpdatesUserInUserTable() throws Exception {
        User user = new User();
        user.setUsername("Test01");
        user.setPassword("Test01");
        user.setIsAdmin(false);
        User returnedUser = userDAO.getUserWithUsername("Test01");
        Assert.assertTrue(returnedUser == null);
        userDAO.insert(user);
        returnedUser = userDAO.getUserWithUsername("Test01");
        Assert.assertTrue(returnedUser.getUsername().equals("Test01"));
        returnedUser.setUsername("Test02");
        int userId = returnedUser.getUserId();
        userDAO.update(returnedUser);
        returnedUser = userDAO.getUserWithId(userId);
        Assert.assertTrue(returnedUser.getUsername().equals("Test02"));
    }

    /**
     * Tests delete for User table
     * @throws Exception
     */
    @Test
    public void deleteUserRemovessUserFromUserTable() throws Exception {
        User user = new User();
        user.setUsername("Test01");
        user.setPassword("Test01");
        user.setIsAdmin(false);
        User returnedUser = userDAO.getUserWithUsername("Test01");
        Assert.assertTrue(returnedUser == null);
        userDAO.insert(user);
        returnedUser = userDAO.getUserWithUsername("Test01");
        Assert.assertTrue(returnedUser.getUsername().equals("Test01"));
        userDAO.delete(returnedUser);
        returnedUser = userDAO.getUserWithUsername("Test01");
        Assert.assertTrue(returnedUser == null);
    }

    /**
     * Tests insert for Flight table
     * @throws Exception
     */
    @Test
    public void insertFlightAddsFlightToFlightTable() throws Exception {
        Flight flight = new Flight();
        flight.setOrigin("ORIGIN");
        flight.setDestination("DESTINATION");
        flight.setDeparture(new Date());
        flight.setArrival(new Date());
        List<Flight> flights = flightDAO.getFlights();
        Assert.assertTrue(flights.size() == 0);
        flightDAO.insert(flight);
        flights = flightDAO.getFlights();
        Assert.assertTrue(flights.size() == 1);
        Assert.assertTrue(flights.get(0).getOrigin().equals("ORIGIN"));
    }

    /**
     * Tests update for Flight table
     * @throws Exception
     */
    @Test
    public void updateFlightUpdatesFlightInFlightTable() throws Exception {
        Flight flight = new Flight();
        flight.setOrigin("ORIGIN");
        flight.setDestination("DESTINATION");
        flight.setDeparture(new Date());
        flight.setArrival(new Date());
        List<Flight> flights = flightDAO.getFlights();
        Assert.assertTrue(flights.size() == 0);
        flightDAO.insert(flight);
        flights = flightDAO.getFlights();
        Assert.assertTrue(flights.size() == 1);
        Assert.assertTrue(flights.get(0).getOrigin().equals("ORIGIN"));
        Flight flightToUpdate = flights.get(0);
        flightToUpdate.setOrigin("TEST");
        flightDAO.update(flightToUpdate);
        flights = flightDAO.getFlights();
        Assert.assertTrue(flights.size() == 1);
        Assert.assertTrue(flights.get(0).getOrigin().equals("TEST"));
    }

    /**
     * Tests delete for Flight table
     * @throws Exception
     */
    @Test
    public void deleteFlightRemovesFlightFromFlightTable() throws Exception {
        Flight flight = new Flight();
        flight.setOrigin("ORIGIN");
        flight.setDestination("DESTINATION");
        flight.setDeparture(new Date());
        flight.setArrival(new Date());
        List<Flight> flights = flightDAO.getFlights();
        Assert.assertTrue(flights.size() == 0);
        flightDAO.insert(flight);
        flights = flightDAO.getFlights();
        Assert.assertTrue(flights.size() == 1);
        Assert.assertTrue(flights.get(0).getOrigin().equals("ORIGIN"));
        flightDAO.delete(flights.get(0));
        flights = flightDAO.getFlights();
        Assert.assertTrue(flights.size() == 0);
    }

    /**
     * Tests insert for Bookings table
     * @throws Exception
     */
    @Test
    public void insertBookingAddsBookingToBookingsTable() throws Exception {
        Booking booking = new Booking();
        booking.setUserId(1);
        booking.setFlightId(1);
        booking.setCreateTime(new Date());
        List<Booking> bookings = bookingsDAO.getBookings();
        Assert.assertTrue(bookings.size() == 0);
        bookingsDAO.insert(booking);
        bookings = bookingsDAO.getBookings();
        Assert.assertTrue(bookings.size() == 1);
        Assert.assertTrue(bookings.get(0).getUserId() == 1 && bookings.get(0).getFlightId() == 1);
    }

    /**
     * Tests update for Bookings table
     * @throws Exception
     */
    @Test
    public void updateBookingUpdatesBookingInBookingsTable() throws Exception {
        Booking booking = new Booking();
        booking.setUserId(1);
        booking.setFlightId(1);
        booking.setCreateTime(new Date());
        List<Booking> bookings = bookingsDAO.getBookings();
        Assert.assertTrue(bookings.size() == 0);
        bookingsDAO.insert(booking);
        bookings = bookingsDAO.getBookings();
        Assert.assertTrue(bookings.size() == 1);
        Booking bookingToTest = bookings.get(0);
        Assert.assertTrue(bookingToTest.getUserId() == 1 && bookingToTest.getFlightId() == 1);
        bookingToTest.setUserId(2);
        bookingToTest.setFlightId(2);
        bookingsDAO.update(bookingToTest);
        Assert.assertTrue(bookings.size() == 1);
        Assert.assertTrue(bookings.get(0).getUserId() == 2 && bookings.get(0).getFlightId() == 2);
    }

    /**
     * Tests delete for Bookings table
     * @throws Exception
     */
    @Test
    public void deleteBookingRemovesBookingFromBookingsTable() throws Exception {
        Booking booking = new Booking();
        booking.setUserId(1);
        booking.setFlightId(1);
        booking.setCreateTime(new Date());
        List<Booking> bookings = bookingsDAO.getBookings();
        Assert.assertTrue(bookings.size() == 0);
        bookingsDAO.insert(booking);
        bookings = bookingsDAO.getBookings();
        Assert.assertTrue(bookings.size() == 1);
        Assert.assertTrue(bookings.get(0).getUserId() == 1 && bookings.get(0).getFlightId() == 1);
        bookingsDAO.delete(bookings.get(0));
        bookings = bookingsDAO.getBookings();
        Assert.assertTrue(bookings.size() == 0);
    }

}