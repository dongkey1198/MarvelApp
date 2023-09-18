package com.example.data.local.datasource

import com.example.domain.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {
    fun getFavoriteCharacters(): Flow<FavoriteCharacter>
}