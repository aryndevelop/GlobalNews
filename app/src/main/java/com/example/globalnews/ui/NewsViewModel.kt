package com.example.globalnews.ui

import androidx.lifecycle.ViewModel
import com.example.globalnews.respository.NewsRepository

class NewsViewModel (
    val newsRepository: NewsRepository
) : ViewModel() {
}