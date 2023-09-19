package com.example.marvelapp.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FlowExtensions {

    fun <T> Flow<T>.throttleFirst(throttleDuration: Long = 1000L): Flow<T> = flow {
        var lastEmissionTime = 0L
        collect { upstream ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastEmissionTime > throttleDuration) {
                lastEmissionTime = currentTime
                emit(upstream)
            }
        }
    }
}