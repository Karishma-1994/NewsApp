package com.example.newsapp.ui.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsRepository

class HeadlinesViewModelFactory (
    private val newsRepository: NewsRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeadlinesViewModel::class.java)) {
            return HeadlinesViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}