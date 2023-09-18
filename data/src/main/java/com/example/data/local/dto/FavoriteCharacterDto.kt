package com.example.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "FavoriteCharacter")
data class FavoriteCharacterDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val isFavorite: Boolean = false,
    val savedDate: Date
)
