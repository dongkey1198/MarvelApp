package com.example.domain.model

sealed class SearchResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Loading<T>(data: T? = null): SearchResult<T>()
    class Success<T>(data: T): SearchResult<T>(data)
    class Error<T>(message: String, data: T? = null,): SearchResult<T>(data, message)
}