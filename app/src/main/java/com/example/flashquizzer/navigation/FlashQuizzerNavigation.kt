package com.example.flashquizzer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.ui.homepage.HomePageView
import com.example.flashquizzer.ui.login.LoginView
import com.example.flashquizzer.ui.profile.ProfileView
import com.example.flashquizzer.ui.register.RegisterView
import com.example.flashquizzer.ui.takequiz.TakeQuizView
import com.example.flashquizzer.ui.uploaddoc.UploadDocView
import com.example.flashquizzer.ui.viewflashcards.ViewFlashcardsView

@Composable
fun FlashQuizzerNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    barIsVisible: (Boolean) -> Unit = { input -> input }
) {
    NavHost(
        navController = navController,
        startDestination = FlashQuizzerDestinations.Login.route,
        modifier = modifier
    ) {
        composable(FlashQuizzerDestinations.Home.route) {
            barIsVisible(true)
            HomePageView(navController)
        }

        composable(FlashQuizzerDestinations.UploadDoc.route) {
            barIsVisible(true)
            UploadDocView(navController = navController)
        }

        composable(FlashQuizzerDestinations.ViewFlashcards.route) {
            barIsVisible(true)
            ViewFlashcardsView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.TakeQuiz.route) {
            barIsVisible(true)
            TakeQuizView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.Login.route) {
            barIsVisible(false)
            LoginView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.Register.route) {
            barIsVisible(false)
            RegisterView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.Profile.route) {
            barIsVisible(true)
            ProfileView(navController)
        }
    }
}

// Extension function for easier navigation
fun NavHostController.navigateToDestination(destination: FlashQuizzerDestinations) {
    this.navigate(destination.route) {
        // Optional: Add navigation options here
        launchSingleTop = true
        restoreState = true
    }
}