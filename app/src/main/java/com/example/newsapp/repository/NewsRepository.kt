package com.example.newsapp.repository

import com.example.newsapp.api.NewsAPIService
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.models.Article

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getHeadlines(CountryCode: String, PageNumber: Int) =
        NewsAPIService.newsApi.getHeadlines(CountryCode, PageNumber)

    suspend fun searchForNews(searchQuery: String, PageNumber: Int) =
        NewsAPIService.newsApi.searchForNews(searchQuery, PageNumber)


    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

     fun getFavoriteNews() = db.getArticleDao().getAllArticle()

    suspend fun clear(article: Article) = db.getArticleDao().clear()

}