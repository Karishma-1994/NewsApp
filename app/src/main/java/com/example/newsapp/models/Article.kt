package com.example.newsapp.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article_table")
data class Article(
    @SerializedName("author")val author: String,
    @SerializedName("content")val content: String,
    @SerializedName("description")val description: String,
    @SerializedName("published_at")val publishedAt: String,
    @SerializedName("source")val source: Source,
    @SerializedName("title")val title: String,
    @SerializedName("url")val url: String,
    @SerializedName("url_to_image")val urlToImage: String
)