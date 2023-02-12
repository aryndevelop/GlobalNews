package com.example.globalnews

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)