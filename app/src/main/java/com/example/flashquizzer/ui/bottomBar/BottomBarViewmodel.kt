package com.example.flashquizzer.ui.bottomBar

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

class BottomBarViewmodel : ViewModel() {
    fun navigateToDestination(navController: NavHostController, screen: FlashQuizzerDestinations) {
        navController.navigate(screen.route) {
            launchSingleTop = true
        }
    }
}
