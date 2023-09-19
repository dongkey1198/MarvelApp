package com.example.domain.model

sealed class RequestResult<T>(
    val data: T? = null,
    val message: String? = null,
    val isProgressing: Boolean = false
) {

    class Loading<T>(isProgressing: Boolean): RequestResult<T>(isProgressing = isProgressing)
    class Success<T>(data: T): RequestResult<T>(data)
    class Error<T>(data: T? = null, message: String): RequestResult<T>(data, message)
}
