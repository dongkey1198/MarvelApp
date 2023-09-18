package com.example.data.local.datasource

import com.example.domain.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {
    suspend fun saveFavoriteCharacter(favoriteCharacter: FavoriteCharacter)
    fun getFavoriteCharacters(): Flow<FavoriteCharacter>
}