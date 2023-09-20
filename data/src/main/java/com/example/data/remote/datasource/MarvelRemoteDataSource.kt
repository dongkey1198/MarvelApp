package com.example.data.remote.datasource

import com.example.data.remote.dto.MarvelCharacterRemoteDto

interface MarvelRemoteDataSource {
    suspend fun fetchCharacters(nameStartsWith: String, offset: Int): MarvelCharacterRemoteDto
}
