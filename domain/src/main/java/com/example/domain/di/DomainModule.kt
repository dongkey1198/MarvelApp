package com.example.domain.di

import com.example.domain.repository.MarvelRepository
import com.example.domain.usecase.DeleteMarvelCharacterUseCase
import com.example.domain.usecase.GetFavoriteCharactersUseCase
import com.example.domain.usecase.SaveMarvelCharacterUseCase
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

    @Provides
    @Singleton
    fun provideSaveCharacterUseCase(
        marvelRepository: MarvelRepository
    ): SaveMarvelCharacterUseCase = SaveMarvelCharacterUseCase(marvelRepository)

    @Provides
    @Singleton
    fun provideDeleteCharacterUseCase(
        marvelRepository: MarvelRepository
    ): DeleteMarvelCharacterUseCase = DeleteMarvelCharacterUseCase(marvelRepository)

    @Provides
    @Singleton
    fun provideGetFavoriteCharactersUseCase(
        marvelRepository: MarvelRepository
    ): GetFavoriteCharactersUseCase = GetFavoriteCharactersUseCase(marvelRepository)
}
