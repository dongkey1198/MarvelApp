package com.example.data.di

import com.example.data.constants.ApiConstants
import com.example.data.remote.datasource.MarvelRemoteDataSource
import com.example.data.remote.datasource.MarvelRemoteDataSourceImpl
import com.example.data.remote.service.MarvelApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideMarvelApiService(): MarvelApiService {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMarvelRemoteDataSource(
        marvelApiService: MarvelApiService
    ): MarvelRemoteDataSource = MarvelRemoteDataSourceImpl(marvelApiService)
}
