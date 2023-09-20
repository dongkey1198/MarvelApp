package com.example.domain.repository

import com.example.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {
    suspend fun fetchCharacters(nameStartsWith: String, offset: Int): Flow<List<MarvelCharacter>>
    suspend fun saveMarvelCharacter(marvelCharacter: MarvelCharacter)
    suspend fun deleteMarvelCharacter(id: Int)
    suspend fun getMarvelAllCharacters(): List<MarvelCharacter>
    fun getMarvelAllCharactersFlow(): Flow<List<MarvelCharacter>>
}
