package com.example.data.extension

object UrlExtensions {

    fun String.convertHttpToHttps(): String =
        if (this.startsWith("http://")) {
            "https://" + this.substring(7)
        } else {
            this
        }
}