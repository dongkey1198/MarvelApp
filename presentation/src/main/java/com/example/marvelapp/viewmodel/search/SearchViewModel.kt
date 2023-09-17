package com.example.marvelapp.viewmodel.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SearchResult
import com.example.domain.usecase.SearchMarvelCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMarvelCharactersUseCase: SearchMarvelCharactersUseCase
) : ViewModel() {

    private val _searchQueryFlow = MutableStateFlow("")

    private val _progressStateFlow = MutableStateFlow(false)
    val progressStateFlow get() = _progressStateFlow.asStateFlow()

    init {
        observeSearchQuery()
    }

    fun performSearch(query: String) {
        _searchQueryFlow.value = query
    }

    private fun observeSearchQuery() {
        _searchQueryFlow
            .debounce(SEARCH_TIMEOUT)
            .distinctUntilChanged()
            .filter { query -> query.length >= 2 }
            .flatMapLatest { query -> searchMarvelCharactersUseCase(query) }
            .onEach { result ->
                when (result) {
                    is SearchResult.Success -> {
                        Log.d("aaa", "${result.data}")
                    }

                    is SearchResult.Loading -> {
                        _progressStateFlow.update { !_progressStateFlow.value }
                    }

                    is SearchResult.Error -> {
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
