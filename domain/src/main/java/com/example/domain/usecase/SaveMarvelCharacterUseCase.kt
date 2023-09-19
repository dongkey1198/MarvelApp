package com.example.domain.usecase

import com.example.domain.model.MarvelCharacter
import com.example.domain.model.RequestResult
import com.example.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveMarvelCharacterUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) {

    operator fun invoke(
        marvelCharacter: MarvelCharacter
    ): Flow<RequestResult<String>> = flow {
        runCatching {
            emit(RequestResult.Loading(true))
            deleteOldestItemIfRequire()
            marvelRepository.saveMarvelCharacter(marvelCharacter)
            emit(RequestResult.Loading(false))
            emit(RequestResult.Success(SUCCESS_MESSAGE))
        }.onFailure {
            emit(RequestResult.Loading(false))
            emit(RequestResult.Error(message = "${it.message}"))
        }
    }

    private suspend fun deleteOldestItemIfRequire() {
        val favoriteCharacters = marvelRepository.getMarvelAllCharacters()
        if (favoriteCharacters.size >= 5) {
            val oldestItem = favoriteCharacters.first()
            marvelRepository.deleteMarvelCharacter(oldestItem.id)
        }
    }

    companion object {
        private const val SUCCESS_MESSAGE = "성공적으로 저장 완료했습니다."
    }
}
