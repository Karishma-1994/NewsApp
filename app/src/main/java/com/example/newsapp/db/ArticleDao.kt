package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.models.Article


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Query("SELECT * from article_table")
    fun getAllArticle(): LiveData<List<Article>>

    @Query("DELETE FROM article_table")
    suspend fun clear()

}