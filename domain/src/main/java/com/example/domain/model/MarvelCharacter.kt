package com.example.domain.model

import java.util.Date

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val isFavorite: Boolean = false,
    val saveDate: Date? = null
)
