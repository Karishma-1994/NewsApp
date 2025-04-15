package com.example.newsapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.ViewResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class SearchViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _searchNews = MutableLiveData<ViewResource<NewsResponse>>()

    val searchNews: LiveData<ViewResource<NewsResponse>>
        get() = _searchNews
    private var searchNewsPage = 1
    private var searchNewsResponse: NewsResponse? = null
    private var newSearchQuery: String? = null
    private var oldSearchQuery: String? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun searchNews(searchKeyword: String) {
        displaySearchNews(searchKeyword)
    }

    private fun displaySearchNews(searchQuery: String) {
        if(searchQuery.isEmpty())
            return
        _searchNews.postValue(ViewResource.Loading())
        coroutineScope.launch {
            try {
                val response = newsRepository.searchForNews(searchQuery, searchNewsPage)
                _searchNews.postValue(handleNewsSearch(response))
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _searchNews.postValue(ViewResource.Error("No Internet"))
                    else -> _searchNews.postValue(ViewResource.Error(e.message.orEmpty()))
                }
            }
        }
    }

    private fun handleNewsSearch(response: Response<NewsResponse>): ViewResource<NewsResponse> {
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
                return ViewResource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return ViewResource.Error(response.message())
    }

    fun retry(searchKeyword: String) {
        displaySearchNews(searchKeyword)
    }
}