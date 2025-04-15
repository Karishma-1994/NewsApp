package com.example.newsapp.ui.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentFavouritesBinding
import com.example.newsapp.databinding.FragmentHeadlinesBinding
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.ui.headlines.HeadlinesViewModel
import com.example.newsapp.ui.headlines.HeadlinesViewModelFactory

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private val db: ArticleDatabase by lazy {
        ArticleDatabase.invoke(requireContext())
    }

    private val repository: NewsRepository by lazy {
        NewsRepository(db)
    }

    private val factory: FavouritesViewModelFactory by lazy {
        FavouritesViewModelFactory(repository)
    }

    private val viewModel: FavouritesViewModel by lazy {
        ViewModelProvider(this, factory)[FavouritesViewModel::class.java]
    }
    private lateinit var adapter: NewsAdapter

    private lateinit var binding: FragmentFavouritesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)

        binding.lifecycleOwner = this
        binding.favouritesViewModel = viewModel

        adapter = NewsAdapter()
        binding.recyclerFavourites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavourites.adapter = adapter


        return binding.root
    }
    }


