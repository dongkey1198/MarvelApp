package com.example.data.local.dao

import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.dto.MarvelCharacterLocalDto
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelCharacterDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun saveMarvelCharacter(marvelCharacterDto: MarvelCharacterLocalDto)

    @Query("DELETE FROM MarvelCharacter WHERE id = :id ")
    suspend fun deleteMarvelCharacter(id: Int)

    @Query("SELECT * FROM MarvelCharacter ORDER BY savedDate ASC")
    suspend fun getMarvelAllCharacters(): List<MarvelCharacterLocalDto>

    @Query("SELECT * FROM MarvelCharacter ORDER BY savedDate ASC")
    fun getMarvelAllCharactersFlow(): Flow<List<MarvelCharacterLocalDto>>
}
