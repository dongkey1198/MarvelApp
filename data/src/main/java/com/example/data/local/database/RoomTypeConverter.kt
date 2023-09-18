package com.example.data.local.database

import androidx.room.TypeConverter
import java.util.Date

class RoomTypeConverter {
    @TypeConverter
    fun fromTimeStamp(timeStamp: Long): Date {
        return Date(timeStamp)
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date): Long {
        return date.time
    }
}