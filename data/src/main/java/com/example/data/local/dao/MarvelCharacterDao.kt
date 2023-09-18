package com.example.data.local.dao

import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.dto.MarvelCharacterDto
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelCharacterDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun saveMarvelCharacter(marvelCharacterDto: MarvelCharacterDto)

    @Query("SELECT * FROM MarvelCharacter")
    fun getMarvelCharacters(): Flow<List<MarvelCharacterDto>>
}
