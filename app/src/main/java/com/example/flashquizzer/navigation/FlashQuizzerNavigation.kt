package com.example.flashquizzer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.ui.homepage.HomePageView
import com.example.flashquizzer.ui.login.LoginView
import com.example.flashquizzer.ui.register.RegisterView
import com.example.flashquizzer.ui.takequiz.TakeQuizView
import com.example.flashquizzer.ui.uploaddoc.UploadDocView
import com.example.flashquizzer.ui.viewflashcards.ViewFlashcardsView





@Composable
fun FlashQuizzerNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    topBarIsVisible: (Boolean) -> Unit = { input -> input }
) {
    NavHost(
        navController = navController,
        startDestination = FlashQuizzerDestinations.Home.route,
        modifier = modifier
    ) {
        composable(FlashQuizzerDestinations.Home.route) {
            topBarIsVisible(false)
            HomePageView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.UploadDoc.route) {
            topBarIsVisible(true) // Changed to true to show TopBar with navigation options
            UploadDocView(
                navController = navController,
                modifier = modifier
            )
        }
        composable(FlashQuizzerDestinations.ViewFlashcards.route) {
            topBarIsVisible(true)
            ViewFlashcardsView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.TakeQuiz.route) {
            topBarIsVisible(true)
            TakeQuizView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.Login.route) {
            topBarIsVisible(false)
            LoginView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.Register.route) {
            topBarIsVisible(false)
            RegisterView(navController, modifier)
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