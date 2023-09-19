package com.example.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.MarvelCharacter
import java.util.Date

@Entity(tableName = "MarvelCharacter")
data class MarvelCharacterLocalDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val savedDate: Date
)

fun MarvelCharacterLocalDto.toDomain(): MarvelCharacter =
    MarvelCharacter(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        isFavorite = true
    )

fun MarvelCharacter.toDto(): MarvelCharacterLocalDto =
    MarvelCharacterLocalDto(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        savedDate = Date()
    )
