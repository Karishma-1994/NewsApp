package com.example.newsapp.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.ui.headlines.HeadlinesViewModel

class FavouritesViewModelFactory (
    private val newsRepository: NewsRepository
    ): ViewModelProvider.Factory{


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
                return FavouritesViewModel(newsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
