package com.example.marvelapp.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.RequestResult
import com.example.domain.usecase.DeleteMarvelCharacterUseCase
import com.example.domain.usecase.SaveMarvelCharacterUseCase
import com.example.domain.usecase.SearchMarvelCharactersUseCase
import com.example.marvelapp.extension.FlowExtensions.throttleFirst
import com.example.marvelapp.model.MarvelCharacterItem
import com.example.marvelapp.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMarvelCharactersUseCase: SearchMarvelCharactersUseCase,
    private val saveMarvelCharacterUseCase: SaveMarvelCharacterUseCase,
    private val deleteMarvelCharacterUseCase: DeleteMarvelCharacterUseCase
) : ViewModel() {

    private val _searchQueryFlow = MutableSharedFlow<String>(replay = 0)
    private val _clickedCharacterFlow = MutableSharedFlow<MarvelCharacterItem>(extraBufferCapacity = 1)

    private val _marvelCharacterItemsFlow = MutableStateFlow<List<MarvelCharacterItem>>(emptyList())
    val marvelCharacterItemsFlow get() = _marvelCharacterItemsFlow.asStateFlow()

    private val _progressStateFlow = MutableStateFlow<Boolean>(false)
    val progressStateFlow get() = _progressStateFlow.asStateFlow()

    private val _resultMessageFlow = MutableSharedFlow<String>(replay = 0)
    val resultMessageFlow get() = _resultMessageFlow.asSharedFlow()

    init {
        observeSearchQuery()
        observeClickedCharacter()
    }

    fun performSearch(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _searchQueryFlow.emit(query)
        }
    }

    fun characterItemClicked(marvelCharacterItem: MarvelCharacterItem) {
        viewModelScope.launch(Dispatchers.Default) {
            _clickedCharacterFlow.emit(marvelCharacterItem)
        }
    }

    private fun observeSearchQuery() {
        _searchQueryFlow
            .debounce(SEARCH_TIMEOUT)
            .flatMapLatest { query ->
                updateProgressingState(true)
                if (query.length >= 2) {
                    searchMarvelCharactersUseCase(query)
                } else {
                    flowOf(RequestResult.Loading(false))
                }
            }
            .onEach { requestResult ->
                when (requestResult) {
                    is RequestResult.Success -> updateMarvelCharacterState(requestResult.data)
                    is RequestResult.Error -> updateResultMassageState(requestResult.message)
                    is RequestResult.Loading -> updateProgressingState(requestResult.isProgressing)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeClickedCharacter() {
        _clickedCharacterFlow
            .throttleFirst()
            .flatMapLatest { marvelCharacterItem ->
                if (marvelCharacterItem.isFavorite) {
                    deleteMarvelCharacterUseCase(marvelCharacterItem.id)
                } else {
                    saveMarvelCharacterUseCase(marvelCharacterItem.toDomain())
                }
            }.onEach { requestResult ->
               when (requestResult) {
                   is RequestResult.Success -> updateResultMassageState(requestResult.data)
                   is RequestResult.Error ->  updateResultMassageState(requestResult.message)
                   is RequestResult.Loading -> updateProgressingState(requestResult.isProgressing)
               }
            }
            .launchIn(viewModelScope)
    }


    private fun updateMarvelCharacterState(marvelCharacters: List<MarvelCharacter>?) {
        marvelCharacters?.let {
            _marvelCharacterItemsFlow.update { marvelCharacters.map { it.toPresentation() } }
        }
    }

    private fun updateProgressingState(isProgressing: Boolean) {
        _progressStateFlow.update { isProgressing }
    }

    private fun updateResultMassageState(message: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            message?.let { _resultMessageFlow.emit(it) }
        }
    }

    companion object {
        private const val SEARCH_TIMEOUT = 300L
    }
}
