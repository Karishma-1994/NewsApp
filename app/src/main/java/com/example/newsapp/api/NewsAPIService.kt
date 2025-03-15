package com.example.newsapp.api

import com.example.newsapp.util.Contants.Companion.BASE_URL
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIService {
    companion object {
        private val gson = Gson()

        // Create a logging interceptor for debugging
        private val loggingInterceptor by lazy {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        }

        // Build the OkHttpClient with the interceptors
        private val okHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

        private val retrofit by lazy {
            Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()
        }

        val newsApi by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}