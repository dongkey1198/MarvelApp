package com.example.domain.usecase

import com.example.domain.model.MarvelCharacter
import com.example.domain.model.RequestResult
import com.example.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteCharactersUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) {

    operator fun invoke(): Flow<RequestResult<List<MarvelCharacter>>> = flow {
        runCatching {
            emit(RequestResult.Loading(true))
            marvelRepository.getMarvelCharacters()
                .collect { favoriteCharacters ->
                    emit(RequestResult.Loading(false))
                    emit(RequestResult.Success(favoriteCharacters))
                }
        }.onFailure {
            emit(RequestResult.Loading(false))
            emit(RequestResult.Error(message = GET_ERROR_MESSAGE))
        }
    }

    companion object {
        private const val GET_ERROR_MESSAGE = "알 수 없는 오류로 데이터를 호출하는데 실패했습니다."
    }
}
