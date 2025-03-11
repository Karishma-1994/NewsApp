package com.example.newsapp.api

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.util.Contants.Companion.API_KEY
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("Country")
        CountryCode: String = "US",
        @Query("Page")
        PageNunber: Int = 1,
        @Query("apiKEY")
        apiKey: String = API_KEY,
    ):NewsResponse

    @GET("")
    suspend fun searchForNews(
        @Query("q")
       searchQuery: String,
        @Query("Page")
        PageNunber: Int = 1,
        @Query("apiKEY")
        apiKey: String = API_KEY,
    ): NewsResponse


}

val api by lazy {
    retrofit.create(NewsAPI::class.java)
}