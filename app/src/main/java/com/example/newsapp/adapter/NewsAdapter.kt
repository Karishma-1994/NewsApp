package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemsNewsBinding
import com.example.newsapp.models.Article
import com.example.newsapp.util.loadNewsIcon

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private val article: List<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ItemsNewsBinding =
            ItemsNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return article.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
       holder.bind(article[position])


    }

    class ArticleViewHolder(private val binding: ItemsNewsBinding) :
        RecyclerView.ViewHolder(binding.root){

    fun bind(article: Article) {
        binding.newsData = article
        binding.executePendingBindings()
        binding.articleImage.loadNewsIcon(article.urlToImage)

            }
        }

    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }










