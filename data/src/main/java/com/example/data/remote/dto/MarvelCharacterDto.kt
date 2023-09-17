package com.example.data.remote.dto

import com.example.domain.model.MarvelCharacter
import com.google.gson.annotations.SerializedName

data class MarvelCharacterDto(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: Data
) {

    data class Data(
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val results: List<Results>
    )

    data class Results(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("thumbnail") val thumbnail: Thumbnail
    ) {
        fun toDomain(): MarvelCharacter =
            MarvelCharacter(
                id = id,
                name = name,
                description = description,
                thumbnail = thumbnail.path
            )
    }

    data class Thumbnail(@SerializedName("path") val path: String)
}
