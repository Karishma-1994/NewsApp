package com.example.newsapp.ui.headlines

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
import okio.IOException
import retrofit2.Response

class HeadlinesViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val _headlines = MutableLiveData<ViewResource<NewsResponse>>()

    val headlines: LiveData<ViewResource<NewsResponse>>
        get() = _headlines

    private var headlinesPage = 1
    private var headlinesResponse: NewsResponse? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        fetchHeadlines("US")
    }

    private fun fetchHeadlines(countryCode: String) {
        _headlines.postValue(ViewResource.Loading())
        coroutineScope.launch {
            try {
                val response = newsRepository.getHeadlines(countryCode, headlinesPage)
                println("KarishmaTest response $response")
                _headlines.postValue(handleNewsResponse(response))
            } catch (e: Exception) {
                println("KarishmaTest exception $e")
                when (e) {
                    is IOException -> _headlines.postValue(ViewResource.Error("Network Failure"))
                    else -> _headlines.postValue(ViewResource.Error(e.message.orEmpty()))
                }
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

    fun retry(){
        fetchHeadlines("US")

    }
}