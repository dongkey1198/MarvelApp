package com.example.data.di

import com.example.data.local.datasource.MarvelLocalDataSource
import com.example.data.remote.datasource.MarvelRemoteDataSource
import com.example.data.repository.MarvelRepositoryImpl
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
        marvelRemoteDataSource: MarvelRemoteDataSource,
        marvelLocalDataSource: MarvelLocalDataSource
    ): MarvelRepository = MarvelRepositoryImpl(marvelRemoteDataSource, marvelLocalDataSource)
}
