package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.local.dto.FavoriteCharacterDto
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {

    @Query("SELECT * FROM FavoriteCharacter")
    fun getFavoriteCharacters(): Flow<FavoriteCharacterDto>
}
