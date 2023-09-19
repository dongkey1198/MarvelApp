package com.example.data.local.datasource

import com.example.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelLocalDataSource {
    suspend fun saveMarvelCharacter(marvelCharacter: MarvelCharacter)
    suspend fun deleteMarvelCharacter(id: Int)
    suspend fun getCharacterCount(): Int
    fun getMarvelCharacters(): Flow<List<MarvelCharacter>>
}
