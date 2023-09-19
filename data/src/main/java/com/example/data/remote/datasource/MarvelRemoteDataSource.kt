package com.example.data.remote.datasource

import com.example.domain.model.MarvelCharacter

interface MarvelRemoteDataSource {
    suspend fun fetchCharacters(nameStartsWith: String, offset: Int): List<MarvelCharacter>
}
