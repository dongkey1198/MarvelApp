package com.example.data.repository

import com.example.data.local.datasource.MarvelLocalDataSource
import com.example.data.remote.datasource.MarvelRemoteDataSource
import com.example.data.remote.dto.toDomain
import com.example.domain.model.MarvelCharacter
import com.example.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val marvelRemoteDataSource: MarvelRemoteDataSource,
    private val favoriteLocalDataSource: MarvelLocalDataSource
) : MarvelRepository {

    override suspend fun fetchCharacters(
        nameStartsWith: String,
        offset: Int
    ): Flow<List<MarvelCharacter>> = flow {
        val response = marvelRemoteDataSource.fetchCharacters(nameStartsWith, offset)
        if (response.code == 200) {
            emit(response.data.results.map { it.toDomain() })
        } else {
            throw Exception()
        }
    }

    override suspend fun saveMarvelCharacter(marvelCharacter: MarvelCharacter) {
        favoriteLocalDataSource.saveMarvelCharacter(marvelCharacter)
    }

    override suspend fun deleteMarvelCharacter(id: Int) {
        favoriteLocalDataSource.deleteMarvelCharacter(id)
    }

    override suspend fun getMarvelAllCharacters(): List<MarvelCharacter> {
        return favoriteLocalDataSource.getMarvelAllCharacters()
    }

    override fun getMarvelAllCharactersFlow(): Flow<List<MarvelCharacter>> {
        return favoriteLocalDataSource.getMarvelAllCharactersFlow()
    }
}
