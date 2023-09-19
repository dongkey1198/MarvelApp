package com.example.data.local.datasource

import com.example.data.local.dao.MarvelCharacterDao
import com.example.data.local.dto.toDomain
import com.example.data.local.dto.toDto
import com.example.domain.model.MarvelCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelLocalDatasourceImpl @Inject constructor(
    private val favoriteCharacterDao: MarvelCharacterDao
) : MarvelLocalDataSource {
    override suspend fun saveMarvelCharacter(
        marvelCharacter: MarvelCharacter
    ) {
        withContext(Dispatchers.IO) {
            favoriteCharacterDao.saveMarvelCharacter(marvelCharacter.toDto())
        }
    }

    override suspend fun deleteMarvelCharacter(id: Int) {
        withContext(Dispatchers.IO) {
            favoriteCharacterDao.deleteMarvelCharacter(id)
        }
    }

    override suspend fun getMarvelAllCharacters(): List<MarvelCharacter> = withContext(Dispatchers.IO) {
        favoriteCharacterDao.getMarvelAllCharacters()
            .map { it.toDomain() }
    }

    override fun getMarvelCharacters(): Flow<List<MarvelCharacter>> =
        favoriteCharacterDao.getMarvelCharacters()
            .map { it.map { result -> result.toDomain() } }
}
