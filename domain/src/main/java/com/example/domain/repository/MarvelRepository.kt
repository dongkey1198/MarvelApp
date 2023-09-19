package com.example.domain.repository

import com.example.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {
    suspend fun fetchCharacters(nameStartsWith: String, offset: Int): List<MarvelCharacter>
    suspend fun saveMarvelCharacter(marvelCharacter: MarvelCharacter)
    suspend fun deleteMarvelCharacter(id: Int)
    suspend fun getMarvelAllCharacters(): List<MarvelCharacter>
    fun getMarvelCharacters(): Flow<List<MarvelCharacter>>
}
