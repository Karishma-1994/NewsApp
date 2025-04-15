package com.example.newsapp.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.ViewResource

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val db: ArticleDatabase by lazy {
        ArticleDatabase.invoke(requireContext())
    }

    private val repository: NewsRepository by lazy {
        NewsRepository(db)
    }

    private val factory: SearchViewModelFactory by lazy {
        SearchViewModelFactory(repository)
    }

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this, factory)[SearchViewModel::class.java]
    }
    private lateinit var adapter: NewsAdapter

    private lateinit var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        binding.lifecycleOwner = this
        binding.searchViewModel = viewModel

        adapter = NewsAdapter()
        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSearch.adapter = adapter


        binding.searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(binding.searchEdit)
                viewModel.searchNews(getSearchKeyword())
                true
            } else {
                false
            }
        }

        binding.itemSearchError.retryButton.setOnClickListener {
            viewModel.retry(getSearchKeyword())
        }

        handleSearchResponse()

        return binding.root
    }

    private fun getSearchKeyword() = binding.searchEdit.text.toString()

    private fun handleSearchResponse() {
        viewModel.searchNews.observe(viewLifecycleOwner) {
            it?.let { viewResource ->
                when (viewResource) {
                    is ViewResource.Success -> {
                        adapter.setNewsResponse(it.data!!.articles)
                        binding.itemSearchError.root.visibility = View.GONE
                        binding.recyclerSearch.visibility = View.VISIBLE
                        binding.paginationProgressBar.visibility = View.GONE
                    }

                    is ViewResource.Error -> {
                        binding.itemSearchError.root.visibility = View.VISIBLE
                        binding.recyclerSearch.visibility = View.GONE
                        binding.paginationProgressBar.visibility = View.GONE
                    }

                    is ViewResource.Loading -> {
                        binding.itemSearchError.root.visibility = View.GONE
                        binding.recyclerSearch.visibility = View.GONE
                        binding.paginationProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
