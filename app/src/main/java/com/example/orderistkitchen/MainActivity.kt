package com.example.orderistkitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.destination_receivedOrders -> {
                    navController.popBackStack(R.id.destination_receivedOrders, false)
                    navController.navigate(R.id.destination_receivedOrders)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.destination_menuManagement -> {
                    navController.popBackStack(R.id.destination_menuManagement, false)
                    navController.navigate(R.id.destination_menuManagement)
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }
    }
}