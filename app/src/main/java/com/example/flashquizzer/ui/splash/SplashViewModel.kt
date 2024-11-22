package com.example.flashquizzer.ui.splash

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.model.AuthState
import com.example.flashquizzer.navigation.FlashQuizzerDestinations
import kotlinx.coroutines.delay

class SplashViewModel(): ViewModel() {
    suspend fun waitAndNavigate(navHostController: NavHostController, authState: AuthState) {
        delay(2000)
        when (authState) {
            is AuthState.Authenticated -> navHostController.navigate(
                FlashQuizzerDestinations.Home.route
            )
            else -> navHostController.navigate(
                FlashQuizzerDestinations.Login.route
            )
        }
    }
}