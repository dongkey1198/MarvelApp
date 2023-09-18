package com.example.data.local.datasource

import com.example.data.local.dao.FavoriteCharacterDao
import com.example.data.local.dto.toDto
import com.example.domain.model.FavoriteCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteLocalDatasourceImpl @Inject constructor(
    private val favoriteCharacterDao: FavoriteCharacterDao
) : FavoriteLocalDataSource {
    override suspend fun saveFavoriteCharacter(favoriteCharacter: FavoriteCharacter) {
        withContext(Dispatchers.IO) {
            favoriteCharacterDao.saveFavoriteCharacter(favoriteCharacter.toDto())
        }
    }

    override fun getFavoriteCharacters(): Flow<FavoriteCharacter> =
        favoriteCharacterDao.getFavoriteCharacters().map { it.toDomain() }
}
