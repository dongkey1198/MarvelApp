package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.FavoriteCharacterDao
import com.example.data.local.database.UserDatabase
import com.example.data.local.datasource.FavoriteLocalDatasourceImpl
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
    fun provideFavoriteDao(
        database: UserDatabase
    ): FavoriteCharacterDao = database.getFavoriteCharacterDao()


    @Provides
    @Singleton
    fun provideFavoriteLocalDataSource(
        favoriteCharacterDao: FavoriteCharacterDao
    ) = FavoriteLocalDatasourceImpl(favoriteCharacterDao)
}