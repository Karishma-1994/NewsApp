package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemsNewsBinding
import com.example.newsapp.models.Article
import com.example.newsapp.util.loadNewsIcon

class NewsAdapter(
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private var articles: List<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ItemsNewsBinding =
            ItemsNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    fun setNewsResponse(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    class ArticleViewHolder(
        private val binding: ItemsNewsBinding,
        private val onItemClickListener: OnItemClickListener? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                newsData = article
                executePendingBindings()
                articleImage.loadNewsIcon(article.urlToImage)
                clArticle.setOnClickListener {
                    onItemClickListener?.onItemClick(bindingAdapterPosition)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
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










