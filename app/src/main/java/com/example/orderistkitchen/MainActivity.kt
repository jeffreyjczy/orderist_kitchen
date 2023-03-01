package com.example.orderistkitchen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

//    private lateinit var navController: NavController
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val database = Firebase.database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message11")
        myRef.setValue("Helloorld!")



//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)
//
//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.destination_receivedOrders -> {
//                    navController.popBackStack(R.id.destination_receivedOrders, false)
//                    navController.navigate(R.id.destination_receivedOrders)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.destination_menuManagement -> {
//                    navController.popBackStack(R.id.destination_menuManagement, false)
//                    navController.navigate(R.id.destination_menuManagement)
//                    return@setOnNavigationItemSelectedListener true
//                }
//
//            }
//            false
//        }
    }
}