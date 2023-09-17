package com.example.marvelapp.utils

import android.view.View

object Utils {

    fun View.setVisibility(isShow: Boolean) {
        when (isShow) {
            true -> this.visibility = View.VISIBLE
            else -> this.visibility = View.GONE
        }
    }
}
