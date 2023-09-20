package com.example.presentation.viewmodel.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.RequestResult
import com.example.domain.usecase.DeleteMarvelCharacterUseCase
import com.example.domain.usecase.GetFavoriteCharactersUseCase
import com.example.presentation.extension.FlowExtensions.throttleFirst
import com.example.presentation.model.MarvelCharacterItem
import com.example.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val deleteMarvelCharacterUseCase: DeleteMarvelCharacterUseCase
): ViewModel() {

    private val _clickedCharacterFlow = MutableSharedFlow<MarvelCharacterItem>()

    private val _favoriteMarvelCharacterFlow = MutableStateFlow<List<MarvelCharacterItem>>(emptyList())
    val favoriteMarvelCharacterFlow get() = _favoriteMarvelCharacterFlow.asStateFlow()

    private val _progressingStateFlow = MutableStateFlow<Boolean>(false)
    val progressStateFlow get() =  _progressingStateFlow.asStateFlow()

    private val _resultMessageFlow = MutableSharedFlow<String>()
    val resultMessageFlow get() = _resultMessageFlow.asSharedFlow()

    private val _emptyMessageStateFlow = MutableStateFlow<Boolean>(false)
    val emptyMessageStateFlow get() = _emptyMessageStateFlow.asStateFlow()

    init {
        observeFavoriteCharacters()
        observeClickedCharacter()
    }

    fun characterItemClicked(marvelCharacterItem: MarvelCharacterItem) {
        viewModelScope.launch(Dispatchers.Default) {
            _clickedCharacterFlow.emit(marvelCharacterItem)
        }
    }

    private fun observeFavoriteCharacters() {
        viewModelScope.launch(Dispatchers.Default) {
            getFavoriteCharactersUseCase().collect { requestResult ->
                when (requestResult) {
                    is RequestResult.Success -> updateFavoriteMarvelCharacterState(requestResult.data)
                    is RequestResult.Loading -> updateProgressingState(requestResult.isProgressing)
                    is RequestResult.Error -> updateResultMassageState(requestResult.message)
                }
            }
        }
    }

    private fun observeClickedCharacter() {
        _clickedCharacterFlow
            .throttleFirst()
            .flatMapLatest { marvelCharacterItem -> deleteMarvelCharacterUseCase(marvelCharacterItem.id) }
            .onEach { requestResult ->
                when (requestResult) {
                    is RequestResult.Success -> updateResultMassageState(requestResult.data)
                    is RequestResult.Error ->  updateResultMassageState(requestResult.message)
                    is RequestResult.Loading -> updateProgressingState(requestResult.isProgressing)
                }
            }
            .launchIn(viewModelScope)
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

    private fun updateProgressingState(isProgressing: Boolean) {
        _progressingStateFlow.update { isProgressing }
    }
}
