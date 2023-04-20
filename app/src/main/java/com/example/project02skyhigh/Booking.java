package com.example.project02skyhigh;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02skyhigh.DB.AppDatabase;

import java.util.Date;


@Entity(tableName = AppDatabase.BOOKINGS_TABLE)
public class Booking {
    /**
     *author: Aaron Bourdeaux
     *date: 2023/04/07
     *description: Booking.java is a model used to represent Booking objects
     *             that are stored in the Bookings database table
     */
    @PrimaryKey(autoGenerate = true)
    private int mBookingId;
    private int mFlightId;
    private int mUserId;
    private Date CreateTime;

    public int getBookingId() {
        return mBookingId;
    }

    public void setBookingId(int mBookingId) {
        this.mBookingId = mBookingId;
    }

    public int getFlightId() {
        return mFlightId;
    }

    public void setFlightId(int mFlightId) {
        this.mFlightId = mFlightId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }
}
