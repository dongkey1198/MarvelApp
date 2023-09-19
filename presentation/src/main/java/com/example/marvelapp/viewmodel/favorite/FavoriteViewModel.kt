package com.example.marvelapp.viewmodel.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.RequestResult
import com.example.domain.usecase.GetFavoriteCharactersUseCase
import com.example.marvelapp.model.MarvelCharacterItem
import com.example.marvelapp.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase
): ViewModel() {

    private val _favoriteMarvelCharacterFlow = MutableStateFlow<List<MarvelCharacterItem>>(emptyList())
    val favoriteMarvelCharacterFlow get() = _favoriteMarvelCharacterFlow.asStateFlow()

    private val _progressStateFlow = MutableStateFlow<Boolean>(false)
    val progressStateFlow get() =  _progressStateFlow.asStateFlow()

    private val _resultMessageFlow = MutableSharedFlow<String>()
    val resultMessageFlow get() = _resultMessageFlow.asSharedFlow()

    private val _emptyMessageStateFlow = MutableStateFlow<Boolean>(false)
    val emptyMessageStateFlow get() = _emptyMessageStateFlow.asStateFlow()

    init {
        observeFavoriteCharacters()
    }

    private fun observeFavoriteCharacters() {
        viewModelScope.launch(Dispatchers.Default) {
            getFavoriteCharactersUseCase().collect { requestResult ->
                when (requestResult) {
                    is RequestResult.Success -> updateFavoriteMarvelCharacterState(requestResult.data)
                    is RequestResult.Loading -> updateProgressState(requestResult.isProgressing)
                    is RequestResult.Error -> updateResultMassageState(requestResult.message)
                }
            }
        }
    }

    private fun updateFavoriteMarvelCharacterState(favoriteCharacters: List<MarvelCharacter>?) {
        if (favoriteCharacters.isNullOrEmpty()) {
            _favoriteMarvelCharacterFlow.update { emptyList() }
            updateEmptyMessageState(true)
        } else {
            _favoriteMarvelCharacterFlow.update { favoriteCharacters.map { it.toPresentation() } }
            updateEmptyMessageState(false)
        }
    }

    private fun updateEmptyMessageState(isShow: Boolean) {
        _emptyMessageStateFlow.update { isShow }
    }

    private fun updateResultMassageState(message: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            message?.let { _resultMessageFlow.emit(it) }
        }
    }

    private fun updateProgressState(isProgressing: Boolean) {
        _progressStateFlow.update { isProgressing }
    }
}
