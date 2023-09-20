package com.example.data.remote.dto

import com.example.data.extension.UrlExtensions.convertHttpToHttps
import com.example.domain.model.MarvelCharacter
import com.google.gson.annotations.SerializedName

data class MarvelCharacterRemoteDto(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
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
    )

    data class Thumbnail(
        @SerializedName("path") val path: String,
        @SerializedName("extension") val extension: String
    )
}

internal fun MarvelCharacterRemoteDto.Result.toDomain(): MarvelCharacter =
    MarvelCharacter(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail.path}.${thumbnail.extension}".convertHttpToHttps()
    )


