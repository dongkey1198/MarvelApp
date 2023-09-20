package com.example.data.remote.datasource

import com.example.data.remote.dto.MarvelCharacterRemoteDto
import com.example.data.remote.service.MarvelApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelRemoteDataSourceImpl @Inject constructor(
    private val marvelApiService: MarvelApiService
) : MarvelRemoteDataSource {

    override suspend fun fetchCharacters(
        nameStartsWith: String,
        offset: Int
    ): MarvelCharacterRemoteDto {
        return withContext(Dispatchers.IO) {
            marvelApiService.fetchCharacters(nameStartsWith = nameStartsWith, offset = offset)
        }
    }
}
