package com.example.newsapp.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch

class FavouritesViewModel(private val newsRepository: NewsRepository) : ViewModel()  {

//    fun addToFavouritesArticle(article: Article) = viewModelScope.launch {
//        newsRepository.upsert(article)
//    }

    fun getFavouritesNews() = newsRepository.getFavoriteNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.clear(article)
    }
}