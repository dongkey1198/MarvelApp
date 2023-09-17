package com.example.domain.usecase

import com.example.domain.model.MarvelCharacter
import com.example.domain.model.RequestResult
import com.example.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMarvelCharactersUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) {

    operator fun invoke(keyword: String): Flow<RequestResult<List<MarvelCharacter>>> = flow {
        runCatching {
            emit(RequestResult.Loading())
            marvelRepository.fetchCharacters(keyword)
        }.onSuccess { result ->
            emit(RequestResult.Success(result))
            emit(RequestResult.Loading())
        }.onFailure { exception ->
            emit(RequestResult.Error("${exception.message}"))
            emit(RequestResult.Loading())
        }
    }
}
