package com.example.data.repository

import com.example.data.local.datasource.FavoriteLocalDataSource
import com.example.domain.model.FavoriteCharacter
import com.example.domain.repository.FavoriteCharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteCharacterRepositoryImpl @Inject constructor(
    private val favoriteCharacterLocalDataSource: FavoriteLocalDataSource
): FavoriteCharacterRepository {
    override suspend fun saveFavoriteCharacter(favoriteCharacter: FavoriteCharacter) {
        favoriteCharacterLocalDataSource.saveFavoriteCharacter(favoriteCharacter)
    }

    override fun getFavoriteCharacters(): Flow<FavoriteCharacter> =
        favoriteCharacterLocalDataSource.getFavoriteCharacters()
}
