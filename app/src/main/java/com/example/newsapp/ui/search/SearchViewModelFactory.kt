package com.example.newsapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.ui.headlines.HeadlinesViewModel

class SearchViewModelFactory (
    private val newsRepository: NewsRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(newsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
