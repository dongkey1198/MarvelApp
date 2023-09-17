package com.example.data.constants

import com.example.data.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class ApiConstants {
    companion object {
        private const val PRIVATE_KEY = BuildConfig.PRIVATE_KEY
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://gateway.marvel.com"
        const val limit = 10
        val timeStamp = "${Timestamp(System.currentTimeMillis()).time}"

        fun hash(): String {
            val input = "$timeStamp$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray()))
                .toString(16)
                .padStart(32, '0')
        }
    }
}