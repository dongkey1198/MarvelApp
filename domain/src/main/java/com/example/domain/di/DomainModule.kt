package com.example.domain.di

import com.example.domain.repository.MarvelRepository
import com.example.domain.usecase.SearchMarvelCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideSearchCharacterUseCase(
        marvelRepository: MarvelRepository
    ): SearchMarvelCharactersUseCase = SearchMarvelCharactersUseCase(marvelRepository)
}
