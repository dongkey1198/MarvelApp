package com.example.domain.usecase

import com.example.domain.model.MarvelCharacter
import com.example.domain.model.RequestResult
import com.example.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchMarvelCharactersUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) {

    private var marvelCharactersCache: MutableList<MarvelCharacter> = mutableListOf()
    private var previousQuery: String = ""
    private var offset = 0

    suspend operator fun invoke(query: String): Flow<RequestResult<List<MarvelCharacter>>> = flow {
        updateOffset(query)
        runCatching {
            combine(
                flowOf(marvelRepository.fetchCharacters(query, offset)),
                marvelRepository.getMarvelAllCharactersFlow()
            ) { characters, favorites ->
                updatePreviousQuery(query)
                updateMarvelCharacterCache(query, characters, favorites)
                marvelCharactersCache
            }.collect {
                emit(RequestResult.Success(it))
                emit(RequestResult.Loading(false))
            }
        }.onFailure {
            emit(RequestResult.Error( message = NETWORK_ERROR_MESSAGE))
            emit(RequestResult.Loading(false))
        }
    }

    private fun updateOffset(query: String) {
        offset = if (query.checkIsNewSearch(previousQuery)) 0 else offset + 10
    }

    private fun updatePreviousQuery(query: String) {
        if (query.checkIsNewSearch(previousQuery)) {
            marvelCharactersCache.clear()
            previousQuery = query
        }
    }

    private fun updateMarvelCharacterCache(
        query: String,
        newCharacter: List<MarvelCharacter>,
        favoriteCharacters: List<MarvelCharacter>
    ) {
        if (query.checkIsNewSearch(previousQuery)) marvelCharactersCache.clear()
        val result = (marvelCharactersCache + newCharacter.filterDuplicate()).map { character ->
            character.copy(isFavorite = favoriteCharacters.any { fav -> character.id == fav.id })
        }
        marvelCharactersCache.clear()
        marvelCharactersCache.addAll(result)
    }

    private fun List<MarvelCharacter>.filterDuplicate(): List<MarvelCharacter> = filterNot { newCharacters ->
           marvelCharactersCache.any { currentCharacter -> currentCharacter.id == newCharacters.id }
        }

    private fun String.checkIsNewSearch(query: String): Boolean {
        return this != query
    }

    companion object {
        private const val NETWORK_ERROR_MESSAGE = "네트워크 에러로 검색 결과를 불러오지 못했습니다."
    }
}
