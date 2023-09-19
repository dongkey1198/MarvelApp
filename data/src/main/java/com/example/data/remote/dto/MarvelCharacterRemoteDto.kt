package com.example.data.remote.dto

import com.example.domain.model.MarvelCharacter
import com.google.gson.annotations.SerializedName

data class MarvelCharacterRemoteDto(
    @SerializedName("data") val data: Data
) {

    data class Data(
        @SerializedName("results") val results: List<Result>
    )

    data class Result(
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
                thumbnail = convertHttpToHttps("${thumbnail.path}/portrait_xlarge.${thumbnail.extension}")
            )

        private fun convertHttpToHttps(url: String): String =
            if (url.startsWith("http://")) {
                "https://" + url.substring(7)
            } else {
                url
            }
    }

    data class Thumbnail(
        @SerializedName("path") val path: String,
        @SerializedName("extension") val extension: String
    )
}
