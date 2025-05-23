package com.example.newsapp.ui.headlines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentHeadlinesBinding
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.models.Article
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.ViewResource


class HeadlinesFragment : Fragment(R.layout.fragment_headlines) {

    private val db: ArticleDatabase by lazy {
        ArticleDatabase.invoke(requireContext())
    }

    private val repository: NewsRepository by lazy {
        NewsRepository(db)
    }

    private val factory: HeadlinesViewModelFactory by lazy {
        HeadlinesViewModelFactory(repository)
    }

    private val viewModel: HeadlinesViewModel by lazy {
        ViewModelProvider(this, factory)[HeadlinesViewModel::class.java]
    }
    private lateinit var adapter: NewsAdapter

    private lateinit var binding: FragmentHeadlinesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_headlines, container, false)

        binding.lifecycleOwner = this
        binding.headlinesViewModel = viewModel

        adapter = NewsAdapter(getItemClickListener())
        binding.recyclerHeadlines.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerHeadlines.adapter = adapter


        binding.itemHeadlinesError.retryButton.setOnClickListener {
            viewModel.retry()
        }

        handleHeadlineResponse()

        return binding.root
    }

    private fun getItemClickListener(): NewsAdapter.OnItemClickListener {
        return object : NewsAdapter.OnItemClickListener{
            override fun onItemClick(article: Article) {
                findNavController().navigate(
                    HeadlinesFragmentDirections.actionHeadlinesFragmentToArticleFragment(article)
                )
            }
        }
    }

    private fun handleHeadlineResponse() {
        viewModel.headlines.observe(viewLifecycleOwner) {
            it?.let { viewResource ->
                when (viewResource) {
                    is ViewResource.Success -> {
                        adapter.setNewsResponse(it.data!!.articles)
                        binding.itemHeadlinesError.root.visibility = View.GONE
                        binding.mainLayoutHeadlines.visibility = View.VISIBLE
                        binding.paginationProgressBar.visibility = View.GONE
                    }

                    is ViewResource.Error -> {
                        binding.itemHeadlinesError.root.visibility = View.VISIBLE
                        binding.mainLayoutHeadlines.visibility = View.GONE
                        binding.paginationProgressBar.visibility = View.GONE
                    }

                    is ViewResource.Loading -> {
                        binding.itemHeadlinesError.root.visibility = View.GONE
                        binding.mainLayoutHeadlines.visibility = View.GONE
                        binding.paginationProgressBar.visibility = View.VISIBLE


                    }
                }
            }
        }
    }

    

}
