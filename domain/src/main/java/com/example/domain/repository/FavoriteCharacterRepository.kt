package com.example.domain.repository

import com.example.domain.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

interface FavoriteCharacterRepository {
    suspend fun saveFavoriteCharacter(favoriteCharacter: FavoriteCharacter)
    fun getFavoriteCharacters(): Flow<FavoriteCharacter>
}
