package com.example.newsapp.api

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("Country")
        countryCode: String = "US",
        @Query("Page")
        pageNunber: Int = 1,
        @Query("apiKEY")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("Page")
        pageNumber: Int = 1,
        @Query("apiKEY")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}