package com.example.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.databinding.FragmentHeadlinesBinding
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.ViewResource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response


class NewsViewModel(app: Application, private val newsRepository: NewsRepository) : AndroidViewModel(app) {

    private val _headlines = MutableLiveData<ViewResource<NewsResponse>>()

    val headlines: LiveData<ViewResource<NewsResponse>>
        get() = _headlines

    private var headlinesPage = 1
    private var headlinesResponse: NewsResponse? = null

    private val _searchNews = MutableLiveData<ViewResource<NewsResponse>>()

    val searchNews: LiveData<ViewResource<NewsResponse>>
        get() = _searchNews
    private var searchNewsPage = 1
    private var searchNewsResponse: NewsResponse? = null
    private var newSearchQuery: String? = null
    private var oldSearchQuery: String? = null

    lateinit var binding: FragmentHeadlinesBinding

    fun addToFavouritesArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getFavouritesNews() = newsRepository.getFavoriteNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.clear(article)
    }

    private suspend fun fetchHeadlines(countryCode: String) {
        _headlines.postValue(ViewResource.Loading())
        try {
            val response = newsRepository.getHeadlines(countryCode, headlinesPage)
            _headlines.postValue(handleNewsResponse(response))
        } catch (e: Exception) {
            when (e) {
                is IOException -> _headlines.postValue(ViewResource.Error("Network Failure"))
                else -> _headlines.postValue(ViewResource.Error(e.message.orEmpty()))
            }
        }
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): ViewResource<NewsResponse> {
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
                return ViewResource.Success(headlinesResponse ?: resultResponse)
            }
        }
        return ViewResource.Error(response.message())
    }

    private suspend fun searchNews(searchQuery: String) {
        _searchNews.postValue(ViewResource.Loading())
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
}
