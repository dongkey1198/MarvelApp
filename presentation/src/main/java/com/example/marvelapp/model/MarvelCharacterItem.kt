package com.example.marvelapp.model

import com.example.domain.model.MarvelCharacter

data class MarvelCharacterItem(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val isFavorite: Boolean = false
) {

    fun toDomain(): MarvelCharacter =
        MarvelCharacter(
            id = id,
            name = name,
            description = description,
            thumbnail = thumbnail,
            isFavorite = isFavorite
        )
}

fun MarvelCharacter.toPresentation() =
    MarvelCharacterItem(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        isFavorite = isFavorite
    )
