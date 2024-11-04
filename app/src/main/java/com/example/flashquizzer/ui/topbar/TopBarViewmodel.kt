package com.example.flashquizzer.ui.topbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

class TopBarViewmodel: ViewModel() {

    private var title by mutableStateOf("")

    val topBarTitle: (NavHostController) -> String = { navController ->
        when (navController.currentBackStackEntry?.destination?.route) {
            FlashQuizzerDestinations.Home.route -> {
                title = FlashQuizzerDestinations.Home.title
                title
            }
            FlashQuizzerDestinations.UploadDoc.route -> {
                title = FlashQuizzerDestinations.UploadDoc.title
                title
            }
            FlashQuizzerDestinations.ViewFlashcards.route -> {
                title = FlashQuizzerDestinations.ViewFlashcards.title
                title
            }
            FlashQuizzerDestinations.TakeQuiz.route -> {
                title = FlashQuizzerDestinations.TakeQuiz.title
                title
            }
            FlashQuizzerDestinations.Login.route -> {
                title = FlashQuizzerDestinations.Login.title
                title
            }
            FlashQuizzerDestinations.Register.route -> {
                title = FlashQuizzerDestinations.Register.title
                title
            }
            else -> {
                title
            }
        }
    }
}