package com.example.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.FavoriteCharacter
import java.util.Date

@Entity(tableName = "FavoriteCharacter")
data class FavoriteCharacterDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val savedDate: Date
) {

    fun toDomain(): FavoriteCharacter = FavoriteCharacter(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail,
        savedDate = savedDate
    )
}
