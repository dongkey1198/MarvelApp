package com.example.data.repository

import com.example.data.remote.datasource.MarvelRemoteDataSource
import com.example.domain.model.MarvelCharacter
import com.example.domain.repository.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val marvelRemoteDataSource: MarvelRemoteDataSource
) : MarvelRepository {

    override suspend fun fetchCharacters(
        nameStartsWith: String,
        offset: Int
    ): Pair<List<MarvelCharacter>, Int> = marvelRemoteDataSource.fetchCharacters(nameStartsWith, offset)
}
