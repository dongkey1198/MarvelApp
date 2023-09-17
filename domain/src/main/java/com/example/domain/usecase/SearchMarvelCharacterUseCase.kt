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

    private val marvelCharacterList: MutableList<MarvelCharacter> = mutableListOf()
    private var previousQuery: String = ""
    private var total = 0
    private var offset = 0

    operator fun invoke(query: String): Flow<RequestResult<List<MarvelCharacter>>> = flow {
        runCatching {
            emit(RequestResult.Loading())
            if (query.length < 2) {
                Pair(emptyList(), 0)
            } else if (previousQuery.checkIsNewSearch(query)) {
                marvelRepository.fetchCharacters(query, 0)
            } else if (marvelCharacterList.size >= total) {
                Pair(emptyList(), total)
            } else {
                marvelRepository.fetchCharacters(query, offset + 10)
            }
        }.onSuccess { result ->
            val marvelCharacters = result.first
            if (previousQuery.checkIsNewSearch(query)) {
                marvelCharacterList.clear()
                previousQuery = query
                marvelCharacterList.addAll(marvelCharacters)
            } else {
                marvelCharacterList.addAll(marvelCharacters)
            }
            total = result.second
            emit(RequestResult.Success(marvelCharacterList))
            emit(RequestResult.Loading())
        }.onFailure { exception ->
            emit(RequestResult.Error("${exception.message}"))
            emit(RequestResult.Loading())
        }
    }

    private fun String.checkIsNewSearch(query: String): Boolean {
        return query != previousQuery
    }
}
