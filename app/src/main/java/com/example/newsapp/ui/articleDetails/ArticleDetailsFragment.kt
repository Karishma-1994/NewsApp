package com.example.newsapp.ui.articleDetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.google.android.material.snackbar.Snackbar


class ArticleDetailsFragment : Fragment(R.layout.fragment_article) {

    private val db: ArticleDatabase by lazy {
        ArticleDatabase.invoke(requireContext())
    }

    private val repository: NewsRepository by lazy {
        NewsRepository(db)
    }


    private val factory: ArticleDetailsViewModelFactory by lazy {
        ArticleDetailsViewModelFactory(repository)
    }

    private val viewModel: ArticleDetailsViewModel by lazy {
        ViewModelProvider(this, factory)[ArticleDetailsViewModel::class.java]
    }


    private lateinit var binding: FragmentArticleBinding

    val args: ArticleDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false)

        binding.lifecycleOwner = this
        binding.articleDetailsViewModel = viewModel
        val article = args.article

        println("KarishmaTest article in Detail $article")

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener {
            viewModel.addToFavouritesArticle(article)
            Snackbar.make(requireView(), "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }


}