package com.example.data.repository

import com.example.data.local.datasource.MarvelLocalDataSource
import com.example.data.remote.datasource.MarvelRemoteDataSource
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.MarvelDataSet
import com.example.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val marvelRemoteDataSource: MarvelRemoteDataSource,
    private val favoriteLocalDataSource: MarvelLocalDataSource
) : MarvelRepository {

    override suspend fun fetchCharacters(
        nameStartsWith: String,
        offset: Int
    ): MarvelDataSet = marvelRemoteDataSource.fetchCharacters(nameStartsWith, offset)

    override suspend fun saveMarvelCharacter(
        marvelCharacter: MarvelCharacter
    ) {
        favoriteLocalDataSource.saveMarvelCharacter(marvelCharacter)
    }

    override fun getMarvelCharacters(): Flow<List<MarvelCharacter>> = favoriteLocalDataSource.getMarvelCharacters()
}
