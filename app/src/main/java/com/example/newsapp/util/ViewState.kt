package com.example.newsapp.util

sealed class ViewState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class success<T>(data: T) : ViewState<T>(data)
    class Error<T>(message: String, data: T? = null) : ViewState<T>(data, message)
    class Loading<T> : ViewState<T>()


}