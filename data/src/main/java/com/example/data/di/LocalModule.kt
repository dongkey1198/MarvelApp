package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.MarvelCharacterDao
import com.example.data.local.database.UserDatabase
import com.example.data.local.datasource.MarvelLocalDataSource
import com.example.data.local.datasource.MarvelLocalDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideUserDatabase(
        @ApplicationContext context: Context
    ): UserDatabase = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        UserDatabase.USER_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideMarvelCharacterDao(
        userDatabase: UserDatabase
    ): MarvelCharacterDao = userDatabase.getMarvelCharacterDao()

    @Provides
    @Singleton
    fun provideMarvelLocalDataSource(
        marvelCharacterDao: MarvelCharacterDao
    ): MarvelLocalDataSource = MarvelLocalDatasourceImpl(marvelCharacterDao)
}
