package com.example.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.MarvelCharacter
import java.util.Date

@Entity(tableName = "MarvelCharacter")
data class MarvelCharacterDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val savedDate: Date
)

fun MarvelCharacterDto.toDomain(): MarvelCharacter =
    MarvelCharacter(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        isFavorite = true
    )

fun MarvelCharacter.toDto(): MarvelCharacterDto =
    MarvelCharacterDto(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        savedDate = Date()
    )
