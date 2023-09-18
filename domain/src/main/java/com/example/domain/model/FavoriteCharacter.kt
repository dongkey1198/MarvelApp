package com.example.domain.model

import java.util.Date

data class FavoriteCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val savedDate: Date
)
