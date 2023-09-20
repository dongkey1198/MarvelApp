package com.example.presentation.model

import com.example.domain.model.MarvelCharacter
import java.util.Date

data class MarvelCharacterItem(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val isFavorite: Boolean = false
)

fun MarvelCharacterItem.toDomain(): MarvelCharacter =
    MarvelCharacter(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        isFavorite = isFavorite,
        saveDate = Date()
    )

fun MarvelCharacter.toPresentation() =
    MarvelCharacterItem(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        isFavorite = isFavorite
    )
