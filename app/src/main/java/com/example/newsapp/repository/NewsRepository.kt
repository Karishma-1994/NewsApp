package com.example.newsapp.repository

import com.example.newsapp.api.NewsAPIService
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import retrofit2.Response

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getHeadlines(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return NewsAPIService.newsApi.getHeadlines(countryCode, pageNumber)
    }

    suspend fun searchForNews(searchQuery: String, pageNumber: Int): Response<NewsResponse> {
        return NewsAPIService.newsApi.searchForNews(searchQuery, pageNumber)
    }

    suspend fun upsert(article: Article) {
        db.getArticleDao().upsert(article)
    }

    fun getFavoriteNews() {
        db.getArticleDao().getAllArticle()
    }

    suspend fun clear(article: Article) {
        db.getArticleDao().clear()
    }

}