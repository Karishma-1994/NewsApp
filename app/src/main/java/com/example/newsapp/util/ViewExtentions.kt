package com.example.newsapp.util

import android.widget.ImageView
import com.example.newsapp.R
import com.squareup.picasso.Picasso

fun ImageView.loadNewsIcon(newsIcon: String) {
    try {
        //Did to prevent crash when picasso is mocked then load function is not present
        Picasso.get()
            .load(newsIcon)
            .into(this)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }
}