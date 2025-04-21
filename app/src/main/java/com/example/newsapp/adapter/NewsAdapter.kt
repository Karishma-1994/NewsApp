package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemsNewsBinding
import com.example.newsapp.models.Article
import com.example.newsapp.util.loadNewsIcon

class NewsAdapter(
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ItemsNewsBinding =
            ItemsNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setNewsResponse(articles: List<Article>) {
        differ.submitList(articles)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ArticleViewHolder(
        private val binding: ItemsNewsBinding,
        private val onItemClickListener: OnItemClickListener? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                newsData = article
                executePendingBindings()
                articleImage.loadNewsIcon(article.urlToImage)
                clArticle.setOnClickListener {
                    onItemClickListener?.onItemClick(getArticle(bindingAdapterPosition))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }

    private fun getArticle(position: Int): Article {
        return differ.currentList[position]
    }
}
