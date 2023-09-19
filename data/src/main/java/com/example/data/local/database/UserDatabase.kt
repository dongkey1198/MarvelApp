package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.dao.MarvelCharacterDao
import com.example.data.local.dto.MarvelCharacterLocalDto

@Database(
    entities = [MarvelCharacterLocalDto::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverter::class)
abstract class UserDatabase: RoomDatabase() {

    abstract fun getMarvelCharacterDao(): MarvelCharacterDao

    companion object {
        const val USER_DATABASE_NAME = "UserDatabase.db"
    }
}
