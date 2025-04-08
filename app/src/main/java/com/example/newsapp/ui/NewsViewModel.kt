package com.example.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.ViewState
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(app: Application, val newsRepository: NewsRepository) : AndroidViewModel(app) {
    private val _headlines = MutableLiveData<ViewState<NewsResponse>>()


    val headlines: LiveData<ViewState<NewsResponse>>
        get() = _headlines
    var headlinesPage = 1
    var headlinesResponse: NewsResponse? = null


    private val _searchNews = MutableLiveData<ViewState<NewsResponse>>()

    val searchNews: LiveData<ViewState<NewsResponse>>
        get() = _searchNews
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null


    private fun handleNewsResponse(response: Response<NewsResponse>): ViewState<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                headlinesPage++
                if (headlinesResponse == null) {
                    headlinesResponse = resultResponse
                } else {
                    val oldArticles = headlinesResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return ViewState.success(headlinesResponse ?: resultResponse)
            }
        }
        return ViewState.Error(response.message())
    }

    private fun handleNewsSearch(response: Response<NewsResponse>): ViewState<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    searchNewsPage++
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return ViewState.success(searchNewsResponse ?: resultResponse)
            }
        }
            return ViewState.Error(response.message())
    }
        fun addToFavouritesArticle(article: Article) = viewModelScope.launch {
            newsRepository.upsert(article)
        }

        fun getFavouritesNews() = newsRepository.getFavoriteNews()

        fun deleteArticle(article: Article) = viewModelScope.launch {
            newsRepository.clear(article)
        }

}
















