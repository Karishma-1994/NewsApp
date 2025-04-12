package com.example.newsapp.util

sealed class ViewResource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ViewResource<T>(data = data)
    class Error<T>(message: String) : ViewResource<T>(message = message)
    class Loading<T> : ViewResource<T>()
}