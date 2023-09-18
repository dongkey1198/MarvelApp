package com.example.data.remote.datasource

import com.example.domain.model.MarvelDataSet

interface MarvelRemoteDataSource {
    suspend fun fetchCharacters(nameStartsWith: String, offset: Int): MarvelDataSet
}
