package com.example.domain.model

sealed class RequestResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Loading<T>(data: T? = null): RequestResult<T>()
    class Success<T>(data: T): RequestResult<T>(data)
    class Error<T>(message: String, data: T? = null,): RequestResult<T>(data, message)
}
