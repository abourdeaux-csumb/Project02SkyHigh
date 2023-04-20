package com.example.project02skyhigh.DB.typeconverter;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {
    /**
     * Title: DateTypeConverter.java
     * Abstract: Enables storage of Date objects in Room db
     * Author: Aaron Bourdeaux
     * Date: 2023/04/10
     */
    @TypeConverter
    public long convertDateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date convertLongToDate(long time) {
        return new Date(time);
    }
}
