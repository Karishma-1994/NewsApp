package com.example.newsapp.ui.articleDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.ui.headlines.HeadlinesViewModel

class ArticleDetailsViewModelFactory(
    private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleDetailsViewModel::class.java)) {
            return ArticleDetailsViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}