package com.example.data.local.datasource

import com.example.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelLocalDataSource {
    suspend fun saveMarvelCharacter(marvelCharacter: MarvelCharacter)
    suspend fun deleteMarvelCharacter(id: Int)
    suspend fun getMarvelAllCharacters(): List<MarvelCharacter>
    fun getMarvelAllCharactersFlow(): Flow<List<MarvelCharacter>>
}
