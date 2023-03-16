package com.example.globalnews.respository

import com.example.globalnews.api.RetrofitInstance
import com.example.globalnews.db.ArticleDatabase
import com.example.globalnews.models.Article

class NewsRepository (
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}