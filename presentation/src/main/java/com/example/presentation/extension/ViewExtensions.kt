package com.example.marvelapp.extension

import android.view.View

object ViewExtensions {

    fun View.setVisibility(isShow: Boolean) {
        when (isShow) {
            true -> this.visibility = View.VISIBLE
            else -> this.visibility = View.GONE
        }
    }
}
