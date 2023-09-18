package com.example.data.local.datasource

import com.example.data.local.dao.FavoriteCharacterDao
import com.example.domain.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteLocalDatasourceImpl @Inject constructor(
    private val favoriteCharacterDao: FavoriteCharacterDao
) : FavoriteLocalDataSource {

    override fun getFavoriteCharacters(): Flow<FavoriteCharacter> =
        favoriteCharacterDao.getFavoriteCharacters().map { it.toDomain() }
}
