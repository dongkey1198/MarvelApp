package com.example.domain.repository

import com.example.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {

    suspend fun fetchCharacters(nameStartsWith: String, offset: Int): Pair<List<MarvelCharacter>, Int>

    suspend fun saveMarvelCharacter(marvelCharacter: MarvelCharacter)

    fun getMarvelCharacters(): Flow<List<MarvelCharacter>>
}
