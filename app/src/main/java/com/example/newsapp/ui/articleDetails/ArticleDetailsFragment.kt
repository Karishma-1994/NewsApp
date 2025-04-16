package com.example.newsapp.ui.articleDetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding


class ArticleDetailsFragment : Fragment(R.layout.fragment_article) {

    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false)

        val arguments = ArticleDetailsFragmentArgs.fromBundle(requireArguments())

        val url = arguments.articleUrl

        return binding.root
    }


}