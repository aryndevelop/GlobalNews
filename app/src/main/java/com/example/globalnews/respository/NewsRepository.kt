package com.example.globalnews.respository

import com.example.globalnews.api.RetrofitInstance
import com.example.globalnews.db.ArticleDatabase

class NewsRepository (
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}