package com.example.newsapp.ui.articleDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch

class ArticleDetailsViewModel (private val newsRepository: NewsRepository) : ViewModel() {


    fun addToFavouritesArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

}