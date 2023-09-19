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
            val count = marvelRepository.getCharacterCount()
            if (count >= STORAGE_LIMIT) {
                STORAGE_IS_FULL_MESSAGE
            } else {
                marvelRepository.saveMarvelCharacter(marvelCharacter)
                SUCCESS_MESSAGE
            }
        }.onSuccess { message ->
            emit(RequestResult.Loading(false))
            emit(RequestResult.Success(message))
        }.onFailure {
            emit(RequestResult.Loading(false))
            emit(RequestResult.Error(message = "${it.message}"))
        }
    }

    companion object {
        private const val SUCCESS_MESSAGE = "성공적으로 저장 완료했습니다."
        private const val STORAGE_IS_FULL_MESSAGE = "좋아요 캐릭터는 최대 5개까지 저장 가능합니다."
        private const val STORAGE_LIMIT = 5
    }
}
