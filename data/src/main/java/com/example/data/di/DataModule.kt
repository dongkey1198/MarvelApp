package com.example.data.di

import com.example.data.local.datasource.FavoriteLocalDataSource
import com.example.data.remote.datasource.MarvelRemoteDataSource
import com.example.data.repository.FavoriteCharacterRepositoryImpl
import com.example.data.repository.MarvelRepositoryImpl
import com.example.domain.repository.FavoriteCharacterRepository
import com.example.domain.repository.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMarvelRepository(
        marvelRemoteDataSource: MarvelRemoteDataSource
    ): MarvelRepository = MarvelRepositoryImpl(marvelRemoteDataSource)

    @Provides
    @Singleton
    fun provideFavoriteCharacterRepository(
        favoriteCharacterLocalDataSource: FavoriteLocalDataSource
    ): FavoriteCharacterRepository = FavoriteCharacterRepositoryImpl(favoriteCharacterLocalDataSource)
}