package com.example.domain.usecase

import com.example.domain.model.RequestResult
import com.example.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteMarvelCharacterUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) {

    operator fun invoke(id: Int): Flow<RequestResult<String>> = flow {
        runCatching {
            emit(RequestResult.Loading(true))
            marvelRepository.deleteMarvelCharacter(id)
            emit(RequestResult.Loading(false))
            emit(RequestResult.Success(SUCCESS_MESSAGE))
        }.onFailure {
            emit(RequestResult.Loading(false))
            emit(RequestResult.Error(message = DELETE_ERROR_MESSAGE))
        }
    }

    companion object {
        private const val SUCCESS_MESSAGE = "성공적으로 삭제 완료했습니다."
        private const val DELETE_ERROR_MESSAGE = "알 수 없는 오류로 데이터를 삭제하는데 실패했습니다."
    }
}
