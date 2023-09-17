package com.example.marvelapp.model

import com.example.domain.model.MarvelCharacter

data class MarvelCharacterItem(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val isFavorite: Boolean = false
)

fun MarvelCharacter.toPresentation() =
    MarvelCharacterItem(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        isFavorite = isFavorite
    )
