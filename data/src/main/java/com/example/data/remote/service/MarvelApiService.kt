package com.example.data.remote.service

import com.example.data.remote.Constants
import com.example.data.remote.dto.MarvelCharacterDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {

    @GET("/v1/public/characters")
    suspend fun fetchCharacters(
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("nameStartsWith") nameStartsWith: String
    ): MarvelCharacterDto
}
