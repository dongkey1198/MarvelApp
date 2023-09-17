package com.example.domain.repository

import com.example.domain.model.MarvelCharacter

interface MarvelRepository {

    suspend fun fetchCharacters(nameStartsWith: String): List<MarvelCharacter>
}
