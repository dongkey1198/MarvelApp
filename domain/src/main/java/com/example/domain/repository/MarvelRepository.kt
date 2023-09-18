package com.example.domain.repository

import com.example.domain.model.MarvelCharacter
import com.example.domain.model.MarvelDataSet
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {

    suspend fun fetchCharacters(nameStartsWith: String, offset: Int): MarvelDataSet

    suspend fun saveMarvelCharacter(marvelCharacter: MarvelCharacter)

    fun getMarvelCharacters(): Flow<List<MarvelCharacter>>
}
