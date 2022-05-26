package com.example.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.ui.setupActionBarWithNavController

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)

        val appBrConfig = AppBarConfiguration(setOf(
            R.id.favourite_product_menu,
            R.id.best_product_menu,
            R.id.newest_product_menu,
            R.id.category_menu
        ))

        setupActionBarWithNavController(navController,appBrConfig)

        bottomNavigationView.setupWithNavController(navController)
    }
}