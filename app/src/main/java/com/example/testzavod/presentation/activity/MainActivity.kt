package com.example.testzavod.presentation.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.testzavod.R
import com.example.testzavod.databinding.ActivityMainBinding
import com.example.testzavod.utils.Constants
import com.example.testzavod.utils.network.module.NetworkModule
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView
        val host =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = host.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_main,
                R.id.nav_map,
                R.id.nav_profile,

            )
        )
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.registrationFragment ||
                destination.id == R.id.authFragment ||
                destination.id == R.id.editFragment ||
                destination.id == R.id.splashScreenFragment ||
                destination.id == R.id.chatsFragment ||
                destination.id == R.id.cameraFragment



            ) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id in listOf(
                        R.id.nav_main,
                        R.id.nav_map,
                        R.id.nav_profile,
                        R.id.registrationFragment,
                        R.id.authFragment,
                        R.id.editFragment
                    )
                ) {
                    finish()
                } else {
                    navController.navigateUp()
                }
            }
        })
    }
}