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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase
): ViewModel() {

    private val _favoriteMarvelCharacterFlow = MutableStateFlow<List<MarvelCharacterItem>>(emptyList())
    val favoriteMarvelCharacterFlow get() = _favoriteMarvelCharacterFlow


    init {
        observeFavoriteCharacters()
    }

    private fun observeFavoriteCharacters() {
        viewModelScope.launch(Dispatchers.Default) {
            getFavoriteCharactersUseCase().collect { requestResult ->
                when (requestResult) {
                    is RequestResult.Success -> updateFavoriteMarvelCharacterState(requestResult.data)
                    is RequestResult.Loading -> Unit
                    is RequestResult.Error -> Unit
                }
            }
        }
    }

    private fun updateFavoriteMarvelCharacterState(favoriteCharacters: List<MarvelCharacter>?) {
        if (favoriteCharacters.isNullOrEmpty()) {
            _favoriteMarvelCharacterFlow.update { emptyList() }
        } else {
            _favoriteMarvelCharacterFlow.update { favoriteCharacters.map { it.toPresentation() } }
        }
    }
}
