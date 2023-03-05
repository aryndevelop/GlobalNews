package com.example.globalnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.globalnews.R
import com.example.globalnews.databinding.ActivityNewsBinding
import com.example.globalnews.db.ArticleDatabase
import com.example.globalnews.respository.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            // setup bottom navigation with NavGraph
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
            val navController = navHostFragment.navController
            findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)

            val newsRepository = NewsRepository(ArticleDatabase(this@NewsActivity))
            val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
            viewModel = ViewModelProvider(this@NewsActivity, viewModelProviderFactory).get(NewsViewModel::class.java)

        }


    }
}