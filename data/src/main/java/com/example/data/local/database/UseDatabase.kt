package com.example.data.local.database

import androidx.room.Database
import com.example.data.local.dao.FavoriteCharacterDao
import com.example.data.local.dto.FavoriteCharacterDto

@Database(
    entities = [
        FavoriteCharacterDto::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class UseDatabase {

    internal abstract fun getFavoriteCharacterDao(): FavoriteCharacterDao

    companion object {
        const val USER_DATABASE_NAME = "UserDatabase.db"
    }
}
