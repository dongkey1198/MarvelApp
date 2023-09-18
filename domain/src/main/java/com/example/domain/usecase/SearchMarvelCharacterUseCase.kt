package com.example.domain.usecase

import com.example.domain.model.MarvelCharacter
import com.example.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchMarvelCharactersUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) {

    private val marvelCharactersCache: MutableList<MarvelCharacter> = mutableListOf()
    private var previousQuery: String = ""
    private var offset = 0
    private var total = 100

    suspend operator fun invoke(query: String): Flow<List<MarvelCharacter>> {
        if (checkIsLastOffset(query)) return flowOf(marvelCharactersCache)
        updateOffset(query)
        return combine(
            flowOf(marvelRepository.fetchCharacters(query, offset)),
            marvelRepository.getMarvelCharacters()
        ) { marvelDataSet, favorites ->
            val characters = marvelDataSet.characters
            total = marvelDataSet.total
            if (query.checkIsNewSearch(previousQuery)) {
                marvelCharactersCache.clear()
                marvelCharactersCache.addAll(characters)
                previousQuery = query
            } else {
                marvelCharactersCache.addAll(characters)
            }
            marvelCharactersCache.map { it.copy(isFavorite = favorites.contains(it)) }
        }
    }

    private fun updateOffset(query: String) {
        offset = if (query.checkIsNewSearch(previousQuery)) {
            0
        } else {
            offset + 10
        }
    }

    private fun checkIsLastOffset(query: String): Boolean {
        return when {
            query.checkIsNewSearch(previousQuery) -> false
            total <= marvelCharactersCache.size -> true
            else -> false
        }
    }

    private fun String.checkIsNewSearch(query: String): Boolean {
        return this != query
    }
}
