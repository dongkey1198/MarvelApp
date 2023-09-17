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
        nameStartsWith: String,
        offset: Int
    ): Pair<List<MarvelCharacter>, Int> = withContext(Dispatchers.IO) {
        val result = marvelApiService.fetchCharacters(nameStartsWith = nameStartsWith, offset = offset)
        val marvelCharacters = result.data.results.map { it.toDomain() }
        val total = result.data.total
        Pair(marvelCharacters, total)
    }
}
