package com.example.newsapp.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")val articles: MutableList<Article>,
    @SerializedName("status")val status: String,
    @SerializedName("total_result")val totalResults: Int
)