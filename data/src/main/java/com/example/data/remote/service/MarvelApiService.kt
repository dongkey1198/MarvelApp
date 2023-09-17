package com.example.data.remote.service

import com.example.data.constants.ApiConstants
import com.example.data.remote.dto.MarvelCharacterDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {

    @GET("/v1/public/characters")
    suspend fun fetchCharacters(
        @Query("apikey") apikey: String = ApiConstants.API_KEY,
        @Query("ts") timeStamp: String = ApiConstants.timeStamp,
        @Query("hash") hash: String = ApiConstants.hash(),
        @Query("nameStartsWith") nameStartsWith: String
    ): MarvelCharacterDto
}
