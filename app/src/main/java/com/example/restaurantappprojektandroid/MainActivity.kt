package com.example.restaurantappprojektandroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding
    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

         vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        bottomNavigation = vb.bottomNav
        bottomNavigation.setItemIconTintList(null)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        bottomNavigation.setupWithNavController(navHost.navController)

        //behebt den fehler mit dem navigieren zwischen den fragemnten über die BottomNavigation
        bottomNavigation.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.profilFragment) {
                navHost.navController.navigate(R.id.profilFragment)
            } else {
                navHost.navController.navigate(item.itemId)
            }
            true
        }
    }
}