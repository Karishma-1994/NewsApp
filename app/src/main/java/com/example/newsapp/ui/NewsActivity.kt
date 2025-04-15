package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuHeadlines -> {
                    // Handle the "Headlines" item click
                    navController.navigate(R.id.headlinesFragment)
                    true
                }

                R.id.menuFavourites -> {
                    // Handle the "Headlines" item click
                    navController.navigate(R.id.favouritesFragment)
                    true
                }

                R.id.menuSearch -> {
                    // Handle the "Headlines" item click
                    navController.navigate(R.id.searchFragment)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
}
