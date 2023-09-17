package com.example.domain.usecase

import com.example.domain.model.MarvelCharacter
import com.example.domain.model.SearchResult
import com.example.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMarvelCharactersUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) {

    operator fun invoke(keyword: String): Flow<SearchResult<List<MarvelCharacter>>> = flow {
        runCatching {
            marvelRepository.fetchCharacters(keyword)
        }.onSuccess { result ->
            emit(SearchResult.Success(result))
        }.onFailure { exception ->
            emit(SearchResult.Error("${exception.message}"))
        }
    }
}