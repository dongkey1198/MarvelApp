package com.example.marvelapp.viewmodel.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.RequestResult
import com.example.domain.usecase.SearchMarvelCharactersUseCase
import com.example.marvelapp.model.MarvelCharacterItem
import com.example.marvelapp.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMarvelCharactersUseCase: SearchMarvelCharactersUseCase
) : ViewModel() {

    private val _searchQueryFlow = MutableSharedFlow<String>(replay = 0)

    private val _marvelCharacterItems = MutableStateFlow<List<MarvelCharacterItem>>(emptyList())
    val marvelCharacterItems get() = _marvelCharacterItems.asStateFlow()

    private val _progressStateFlow = MutableStateFlow<Boolean>(false)
    val progressStateFlow get() = _progressStateFlow.asStateFlow()

    init {
        observeSearchQuery()
    }

    fun performSearch(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _searchQueryFlow.emit(query)
        }
    }

    private fun observeSearchQuery() {
        _searchQueryFlow
            .debounce(SEARCH_TIMEOUT)
            .flatMapLatest { query ->
                searchMarvelCharactersUseCase(query) }
            .onEach { result ->
                when (result) {
                    is RequestResult.Success -> {
                        val marvelCharacterItems = result.data?.map { it.toPresentation() } ?: emptyList()
                        _marvelCharacterItems.update { marvelCharacterItems }
                    }

                    is RequestResult.Loading -> {
                        _progressStateFlow.update { !_progressStateFlow.value }
                    }

                    is RequestResult.Error -> {
                        Log.d("aaa", "${result.message}")
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val SEARCH_TIMEOUT = 300L
    }
}
