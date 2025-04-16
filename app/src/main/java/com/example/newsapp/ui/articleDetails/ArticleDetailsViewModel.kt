package com.example.newsapp.ui.articleDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.models.Article

class ArticleDetailsViewModel {

    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article>
        get() = _article


}