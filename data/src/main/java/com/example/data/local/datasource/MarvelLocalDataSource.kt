package com.example.data.local.datasource

import com.example.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelLocalDataSource {
    suspend fun saveMarvelCharacter(marvelCharacter: MarvelCharacter)
    fun getMarvelCharacters(): Flow<List<MarvelCharacter>>
}