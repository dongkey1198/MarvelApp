package com.example.data.remote.datasource

import com.example.data.remote.service.MarvelApiService
import com.example.domain.model.MarvelCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelRemoteDataSourceImpl @Inject constructor(
    private val marvelApiService: MarvelApiService
) : MarvelRemoteDataSource {

    override suspend fun fetchCharacters(
        nameStartsWith: String
    ): List<MarvelCharacter> = withContext(Dispatchers.IO) {
        marvelApiService.fetchCharacters(nameStartsWith = nameStartsWith)
            .data
            .results
            .map { result -> result.toDomain() }
    }
}
